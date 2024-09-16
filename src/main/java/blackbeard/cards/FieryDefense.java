package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.DamageAllEnemiesWithDamageMatrixAction;
import blackbeard.enums.CardColorEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackbeard.TheBlackbeardMod.makeID;

public class FieryDefense extends AbstractBlackbeardCard {

    public static final String ID = makeID("FieryDefense");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMOUNT = 7;
    private static final int DAMAGE_AMOUNT = 3;
    private static final int DAMAGE_TIMES = 1;
    private static final int UPGRADE_PLUS_BLOCK_AMOUNT = 2;
    private static final int UPGRADE_PLUS_DAMAGE_TIMES = 1;

    public FieryDefense() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.COMMON, CardTarget.SELF);

        this.baseBlock = this.block = BLOCK_AMOUNT;
        this.baseMagicNumber = this.magicNumber = DAMAGE_TIMES;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new DamageAllEnemiesWithDamageMatrixAction(DAMAGE_AMOUNT));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK_AMOUNT);
            this.upgradeMagicNumber(UPGRADE_PLUS_DAMAGE_TIMES);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
