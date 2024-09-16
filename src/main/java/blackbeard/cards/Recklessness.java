package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.enums.CardColorEnum;
import blackbeard.powers.LoseEnergyNextTurnPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackbeard.TheBlackbeardMod.makeID;

public class Recklessness extends AbstractBlackbeardCard {

    public static final String ID = makeID("Recklessness");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int ENERGY_TO_GET = 2;
    private static final int UPGRADE_PLUS_ENERGY_TO_GET = 1;
    private static final int ENERGY_TO_LOSE_NEXT_TURN = 1;

    public Recklessness() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = ENERGY_TO_GET;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseEnergyNextTurnPower(p, ENERGY_TO_LOSE_NEXT_TURN), ENERGY_TO_LOSE_NEXT_TURN));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_ENERGY_TO_GET);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
