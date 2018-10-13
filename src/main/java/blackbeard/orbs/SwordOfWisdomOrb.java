package blackbeard.orbs;

import blackbeard.TheBlackbeardMod;
import blackbeard.characters.TheBlackbeard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

public class SwordOfWisdomOrb extends WeaponOrb {

    public static final String ID = "blackbeard:SwordOfWisdomOrb";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;

    public SwordOfWisdomOrb(int attack, int durability) {
        super(ID, NAME, DESCRIPTION[0], TheBlackbeardMod.getOrbImagePath(TheBlackbeard.DEFAULT_WEAPON_ORB_ID), attack, durability);
        this.attack = attack;
        this.durability = durability;
    }

    @Override
    public void effectOnUse() {
        super.effectOnUse();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
    }
}
