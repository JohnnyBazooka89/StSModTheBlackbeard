package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class WoodenLeg extends CustomCard {
    public static final String ID = "blackbeard:WoodenLeg";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int STRENGTH = 4;
    private static final int UPGRADED_PLUS_STRENGTH = 1;
    private static final int DEXTERITY_TO_LOSE = 2;

    public WoodenLeg() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.SELF);

        this.baseMagicNumber = STRENGTH;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, -DEXTERITY_TO_LOSE), -DEXTERITY_TO_LOSE));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.baseMagicNumber), this.baseMagicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_PLUS_STRENGTH);
        }
    }
}
