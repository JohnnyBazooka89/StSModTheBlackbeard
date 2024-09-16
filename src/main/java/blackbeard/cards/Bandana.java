package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.enums.CardColorEnum;
import blackbeard.powers.GoldToLootPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackbeard.TheBlackbeardMod.makeID;

public class Bandana extends AbstractBlackbeardCard {
    public static final String ID = makeID("Bandana");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int LOOTING_CAP_TO_GAIN = 25;
    private static final int UPGRADE_PLUS_LOOTING_CAP_TO_GAIN = 5;

    public Bandana() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.POWER,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = LOOTING_CAP_TO_GAIN;

        this.tags.add(CardTags.HEALING); //It doesn't heal, but I don't want gaining Gold to be abused.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new GoldToLootPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_LOOTING_CAP_TO_GAIN);
        }
    }
}
