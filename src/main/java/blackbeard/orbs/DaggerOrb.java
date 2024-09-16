package blackbeard.orbs;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.DaggerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

import static blackbeard.TheBlackbeardMod.makeID;

public class DaggerOrb extends AbstractWeaponOrb {

    public static final String ID = makeID("DaggerOrb");
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;

    public DaggerOrb(int attack, int durability, boolean justAddedUsingAttackCard) {
        super(ID, NAME, DESCRIPTION[0], TheBlackbeardMod.getOrbImagePath(ID), attack, durability, justAddedUsingAttackCard);
        this.attack = attack;
        this.durability = durability;
    }

    @Override
    public void effectAtStartOfTurnPostDraw() {
        AbstractDungeon.actionManager.addToBottom(new DaggerAction(this));
    }

}
