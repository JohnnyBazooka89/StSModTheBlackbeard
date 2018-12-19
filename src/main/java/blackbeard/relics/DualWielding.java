package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import com.badlogic.gdx.graphics.Texture;

public class DualWielding extends CustomRelic {
    public static final String ID = "blackbeard:DualWielding";

    public DualWielding() {
        super(ID, new Texture(TheBlackbeardMod.getRelicImagePath(ID)), new Texture(TheBlackbeardMod.getRelicOutlineImagePath(ID)), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    /* Logic is in WeaponPower. */

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
