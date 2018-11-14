package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import com.badlogic.gdx.graphics.Texture;

public class CannonballsOfSteel extends CustomRelic {
    public static final String ID = "blackbeard:CannonballsOfSteel";

    public CannonballsOfSteel() {
        super(ID, new Texture(TheBlackbeardMod.getRelicImagePath(ID)), new Texture(TheBlackbeardMod.getRelicOutlineImagePath(ID)), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    /* Logic is in CannonballsOfSteelApplyPowersPatch and CannonballsOfSteelCalculateCardDamagePatch. */

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
