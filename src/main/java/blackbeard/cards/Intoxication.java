package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.enums.CardColorEnum;
import blackbeard.powers.IntoxicationPower;
import blackbeard.powers.ResistancePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Intoxication extends AbstractBlackbeardCard {
    public static final String ID = "blackbeard:Intoxication";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int RESISTANCE_VALUE = 4;
    private static final int UPGRADE_PLUS_RESISTANCE_VALUE = 1;
    private static final int RESISTANCE_TO_LOSE_EACH_TURN = 1;

    public Intoxication() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.POWER,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.RARE, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = RESISTANCE_VALUE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ResistancePower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new IntoxicationPower(p, RESISTANCE_TO_LOSE_EACH_TURN), RESISTANCE_TO_LOSE_EACH_TURN));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_RESISTANCE_VALUE);
        }
    }
}
