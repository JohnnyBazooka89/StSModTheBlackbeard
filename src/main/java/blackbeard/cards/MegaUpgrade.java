package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.UpgradeWeaponsAction;
import blackbeard.enums.CardColorEnum;
import blackbeard.enums.WeaponsToUseEnum;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MegaUpgrade extends AbstractBlackbeardCard {

    public static final String ID = "blackbeard:MegaUpgrade";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;
    private static final int UPGRADE_VALUE = 3;
    private static final int UPGRADE_PLUS_UPGRADE_VALUE = 2;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public MegaUpgrade() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.RARE, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = UPGRADE_VALUE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new UpgradeWeaponsAction(this.magicNumber, this.magicNumber, WeaponsToUseEnum.ALL_WEAPONS));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_UPGRADE_VALUE);
        }
    }
}
