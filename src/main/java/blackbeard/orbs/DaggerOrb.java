package blackbeard.orbs;

import blackbeard.TheBlackbeardMod;
import blackbeard.characters.TheBlackbeard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.OrbStrings;

public class DaggerOrb extends WeaponOrb {

    public static final String ID = "blackbeard:DaggerOrb";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;

    public DaggerOrb(int attack, int durability) {
        super(ID, NAME, DESCRIPTION[0], TheBlackbeardMod.getOrbImagePath(TheBlackbeard.DEFAULT_WEAPON_ORB_ID), attack, durability);
        this.attack = attack;
        this.durability = durability;
    }

}
