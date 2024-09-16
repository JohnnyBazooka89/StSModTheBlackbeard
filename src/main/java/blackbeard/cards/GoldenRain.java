package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.GoldenRainAction;
import blackbeard.enums.CardColorEnum;
import blackbeard.interfaces.IGoldenCard;
import blackbeard.utils.GoldenCardsUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackbeard.TheBlackbeardMod.makeID;

public class GoldenRain extends AbstractBlackbeardCard implements IGoldenCard {
    public static final String ID = makeID("GoldenRain");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int PERMILLES_OF_GOLD_GAINED = 25;
    private static final int UPGRADE_PLUS_PERMILLES_OF_GOLD_GAINED = 5;

    public GoldenRain() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.RARE, CardTarget.ALL_ENEMY);

        this.baseMagicNumber = this.magicNumber = PERMILLES_OF_GOLD_GAINED;
        this.isMultiDamage = true;

        setGoldenValuesAndUpdateDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GoldenRainAction(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE), this.damage));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        setGoldenValuesAndUpdateDescription();
        super.calculateCardDamage(mo);
    }

    @Override
    public void applyPowers() {
        setGoldenValuesAndUpdateDescription();
        super.applyPowers();
    }

    @Override
    public void setGoldenValuesAndUpdateDescription() {
        this.baseDamage = this.damage = this.magicNumber * GoldenCardsUtils.getBlackbeardGoldGained() / 1000;
        this.rawDescription = GoldenCardsUtils.getGoldenCardDescription(this.upgraded, DESCRIPTION, UPGRADE_DESCRIPTION, EXTENDED_DESCRIPTION);
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_PERMILLES_OF_GOLD_GAINED);
            setGoldenValuesAndUpdateDescription();
        }
    }
}
