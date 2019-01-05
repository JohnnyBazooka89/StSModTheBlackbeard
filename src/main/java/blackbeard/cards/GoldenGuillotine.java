package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.enums.AbstractCardEnum;
import blackbeard.interfaces.IGoldenCard;
import blackbeard.utils.GoldenCardsUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GoldenGuillotine extends CustomCard implements IGoldenCard {
    public static final String ID = "blackbeard:GoldenGuillotine";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 3;
    private static final int PERCENT_OF_GOLD_GAINED = 4;
    private static final int UPGRADE_PLUS_PERCENT_OF_GOLD_GAINED = 1;

    public GoldenGuillotine() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.RARE, CardTarget.ENEMY);

        this.baseMagicNumber = this.magicNumber = PERCENT_OF_GOLD_GAINED;

        setGoldenValuesAndUpdateDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        setGoldenValuesAndUpdateDescription();
    }

    @Override
    public void setGoldenValuesAndUpdateDescription() {
        this.baseDamage = this.damage = this.magicNumber * GoldenCardsUtil.getBlackbeardGoldGained() / 100;
        this.rawDescription = GoldenCardsUtil.getGoldenCardDescription(this.upgraded, DESCRIPTION, DESCRIPTION, EXTENDED_DESCRIPTION);
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_PERCENT_OF_GOLD_GAINED);
            setGoldenValuesAndUpdateDescription();
        }
    }

}
