package blackbeard.cards;

import basemod.helpers.BaseModCardTags;
import blackbeard.TheBlackbeardMod;
import blackbeard.enums.CardColorEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackbeard.TheBlackbeardMod.makeID;

public class BlackbeardDefend extends AbstractBlackbeardCard {
    public static final String ID = makeID("Defend");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMOUNT = 5;
    private static final int UPGRADE_PLUS_BLOCK_AMOUNT = 3;

    public BlackbeardDefend() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.BASIC, AbstractCard.CardTarget.SELF);

        this.baseBlock = this.block = BLOCK_AMOUNT;

        this.tags.add(CardTags.STARTER_DEFEND);
        this.tags.add(BaseModCardTags.BASIC_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK_AMOUNT);
        }
    }
}
