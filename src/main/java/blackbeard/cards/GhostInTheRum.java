package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.characters.TheBlackbeard;
import blackbeard.patches.AbstractCardEnum;
import blackbeard.powers.ResistancePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class GhostInTheRum extends CustomCard {
    public static final String ID = "blackbeard:GhostInTheRum";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int RESISTANCE_DEBUFF_VALUE = 2;
    private static final int UPGRADED_MINUS_RESISTANCE_DEBUFF_VALUE = 1;

    public GhostInTheRum() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(TheBlackbeard.DEFAULT_SKILL_CARD_ID), COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.SELF);

        this.baseMagicNumber = RESISTANCE_DEBUFF_VALUE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ResistancePower(p, -this.baseMagicNumber), -this.baseMagicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-UPGRADED_MINUS_RESISTANCE_DEBUFF_VALUE);
            this.initializeDescription();
        }
    }
}
