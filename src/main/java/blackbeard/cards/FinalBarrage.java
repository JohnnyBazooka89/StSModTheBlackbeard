package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.ShootAnythingAction;
import blackbeard.enums.CardColorEnum;
import blackbeard.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class FinalBarrage extends AbstractBlackbeardCard {
    public static final String ID = "blackbeard:FinalBarrage";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 0;
    private static final int ATTACK_PER_CANNONBALL = 4;
    private static final int UPGRADED_PLUS_ATTACK_PER_CANNONBALL = 2;

    public FinalBarrage() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.RARE, CardTarget.ENEMY);

        this.baseMagicNumber = this.magicNumber = ATTACK_PER_CANNONBALL;
        this.baseDamage = this.damage = 0;

        if (CardCrawlGame.isInARun() && (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)) {
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
        for (Integer index : indexes) {
            switch (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(index).cardID) {
                case HumongousCannonball.ID:
                    AbstractDungeon.actionManager.addToBottom(new ShootAnythingAction(m, getHumongousCannonballTexture(), true));
                    break;
                case GoldenCannonball.ID:
                    AbstractDungeon.actionManager.addToBottom(new ShootAnythingAction(m, getGoldenCannonballTexture(), true));
                    break;
                default:
                    AbstractDungeon.actionManager.addToBottom(new ShootAnythingAction(m, getCannonballTexture(), true));
                    break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
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
            this.upgradeMagicNumber(UPGRADED_PLUS_ATTACK_PER_CANNONBALL);
        }
    }
}
