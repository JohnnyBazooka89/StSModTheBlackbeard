package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import com.badlogic.gdx.graphics.Texture;

public class Spearhead extends CustomRelic {
    public static final String ID = "blackbeard:Spearhead";

    public Spearhead() {
        super(ID, new Texture(TheBlackbeardMod.getRelicImagePath(ID)), new Texture(TheBlackbeardMod.getRelicOutlineImagePath(ID)), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    /* Logic is in WeaponOrb::getDamageToDeal. */

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
