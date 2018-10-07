package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.characters.TheBlackbeard;
import blackbeard.patches.AbstractCardEnum;
import blackbeard.powers.TheDrunkenSailorPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheDrunkenSailor extends CustomCard {
    public static final String ID = "blackbeard:TheDrunkenSailor";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 0;
    private static final int POTENCY_MULTIPLIER = 2;
    private static final int UPGRADED_PLUS_POTENCY_MULTIPLIER = 1;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public TheDrunkenSailor() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(TheBlackbeard.DEFAULT_SKILL_CARD_ID), COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.RARE, CardTarget.SELF);

        this.baseMagicNumber = POTENCY_MULTIPLIER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TheDrunkenSailorPower(p, this.baseMagicNumber - 1), this.baseMagicNumber - 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_PLUS_POTENCY_MULTIPLIER);
        }
    }
}
