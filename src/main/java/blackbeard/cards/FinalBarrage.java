package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.ShootAnythingAction;
import blackbeard.enums.CardColorEnum;
import blackbeard.enums.CardTagsEnum;
import blackbeard.utils.CombatUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class FinalBarrage extends AbstractBlackbeardCard {
    public static final String ID = "blackbeard:FinalBarrage";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 0;
    private static final int ATTACK_PER_CANNONBALL = 4;
    private static final int UPGRADE_PLUS_ATTACK_PER_CANNONBALL = 2;

    public FinalBarrage() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.RARE, CardTarget.ENEMY);

        this.baseMagicNumber = this.magicNumber = ATTACK_PER_CANNONBALL;
        this.baseDamage = this.damage = 0;

        if (CombatUtils.isIndeedWithoutADoubtInCombat()) {
            setBaseDamageAndUpdateDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (card.hasTag(CardTagsEnum.CANNONBALL)) {
                indexes.add(AbstractDungeon.actionManager.cardsPlayedThisCombat.indexOf(card));
            }
        }
        int count = 0;
        for (int index : indexes) {
            switch (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(index).cardID) {
                case HumongousCannonball.ID:
                    addToBot(new ShootAnythingAction(m, getHumongousCannonballTexture(), true, count));
                    break;
                case GoldenCannonball.ID:
                    addToBot(new ShootAnythingAction(m, getGoldenCannonballTexture(), true, count));
                    break;
                default:
                    addToBot(new ShootAnythingAction(m, getCannonballTexture(), true, count));
                    break;
            }
            count++;
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        setBaseDamageAndUpdateDescription();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        super.triggerOnOtherCardPlayed(c);
        setBaseDamageAndUpdateDescription();
    }

    @Override
    public void applyPowers() {
        setBaseDamageAndUpdateDescription();
        super.applyPowers();
    }

    private void setBaseDamageAndUpdateDescription() {
        int cannonballsPlayedThisCombat = 0;

        for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (card.hasTag(CardTagsEnum.CANNONBALL)) {
                cannonballsPlayedThisCombat++;
            }
        }

        this.baseDamage = this.damage = cannonballsPlayedThisCombat * this.magicNumber;
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_ATTACK_PER_CANNONBALL);
        }
    }
}
