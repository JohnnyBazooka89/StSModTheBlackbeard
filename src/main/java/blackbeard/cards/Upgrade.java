package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.UpgradeWeaponsAction;
import blackbeard.enums.CardColorEnum;
import blackbeard.enums.WeaponsToUseEnum;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackbeard.TheBlackbeardMod.makeID;

public class Upgrade extends AbstractBlackbeardCard {

    public static final String ID = makeID("Upgrade");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADE_VALUE = 1;
    private static final int UPGRADE_PLUS_UPGRADE_VALUE = 1;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public Upgrade() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.SELF);

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
