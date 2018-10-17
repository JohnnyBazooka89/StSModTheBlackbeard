package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.characters.TheBlackbeard;
import blackbeard.patches.AbstractCardEnum;
import blackbeard.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class FinalBarrage extends CustomCard {
    public static final String ID = "blackbeard:FinalBarrage";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 0;
    private static final int ATTACK_PER_CANNONBALL = 6;
    private static final int UPGRADED_PLUS_ATTACK_PER_CANNONBALL = 2;

    public FinalBarrage() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(TheBlackbeard.DEFAULT_ATTACK_CARD_ID), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        this.baseMagicNumber = this.magicNumber = ATTACK_PER_CANNONBALL;
        this.baseDamage = this.damage = 0;

        if (CardCrawlGame.isInARun()) {
            setBaseDamageAndUpgradeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        setBaseDamageAndUpgradeDescription();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        super.triggerOnOtherCardPlayed(c);
        setBaseDamageAndUpgradeDescription();
    }

    private void setBaseDamageAndUpgradeDescription() {
        int cannonballsPlayedThisTurn = 0;
        Iterator<AbstractCard> cardsPlayedThisTurnIt = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();

        while (cardsPlayedThisTurnIt.hasNext()) {
            AbstractCard card = cardsPlayedThisTurnIt.next();
            if (card.hasTag(CardTagsEnum.CANNONBALL)) {
                cannonballsPlayedThisTurn++;
            }
        }

        this.baseDamage = this.damage = cannonballsPlayedThisTurn * this.magicNumber;
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_PLUS_ATTACK_PER_CANNONBALL);
        }
    }
}
