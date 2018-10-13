package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.patches.AbstractCardEnum;
import blackbeard.utils.GoldenCardsUtil;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GoldenDefend extends CustomCard {
    public static final String ID = "blackbeard:GoldenDefend";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMOUNT = 5;
    private static final int UPGRADE_BLOCK_AMOUNT = 3;

    public GoldenDefend() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.COMMON, CardTarget.SELF);

        this.baseMagicNumber = BLOCK_AMOUNT;
        this.baseBlock = BLOCK_AMOUNT;

        if (CardCrawlGame.isInARun()) {
            this.rawDescription = GoldenCardsUtil.getGoldenCardDescription(DESCRIPTION, EXTENDED_DESCRIPTION);
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.baseBlock));
    }

    @Override
    public void applyPowers() {
        this.baseBlock = this.baseMagicNumber + (CardCrawlGame.goldGained / 100);
        super.applyPowers();
        this.rawDescription = GoldenCardsUtil.getGoldenCardDescription(DESCRIPTION, EXTENDED_DESCRIPTION) + EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        this.rawDescription = GoldenCardsUtil.getGoldenCardDescription(DESCRIPTION, EXTENDED_DESCRIPTION);
        this.initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_BLOCK_AMOUNT);
        }
    }
}
