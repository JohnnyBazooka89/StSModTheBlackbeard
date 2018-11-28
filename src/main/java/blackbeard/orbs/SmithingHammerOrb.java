package blackbeard.orbs;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.SmithingHammerAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

public class SmithingHammerOrb extends WeaponOrb {

    public static final String ID = "blackbeard:SmithingHammerOrb";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;

    public SmithingHammerOrb(int attack, int durability, boolean justAddedUsingAttackCard) {
        super(ID, NAME, DESCRIPTION[0], TheBlackbeardMod.getOrbImagePath(ID), attack, durability, justAddedUsingAttackCard);
        this.attack = attack;
        this.durability = durability;
    }

    @Override
    public void effectOnUse() {
        super.effectOnUse();
        AbstractDungeon.actionManager.addToBottom(new UpgradeRandomCardAction());
    }

    public void effectAtStartOfTurnPostDraw() {
        super.effectAtStartOfTurnPostDraw();
        AbstractDungeon.actionManager.addToBottom(new SmithingHammerAction(this));
    }

}
