package blackbeard.orbs;

import blackbeard.TheBlackbeardMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.OrbStrings;

public class HalberdOrb extends WeaponOrb {

    public static final String ID = "blackbeard:HalberdOrb";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;

    public HalberdOrb(int attack, int durability) {
        super(ID, NAME, DESCRIPTION[0], TheBlackbeardMod.getOrbImagePath(ID), attack, durability);
        this.attack = attack;
        this.durability = durability;
    }

}
