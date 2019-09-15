package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

public class CannonballsOfSteel extends CustomRelic {

    public static final String ID = "blackbeard:CannonballsOfSteel";
    private static final Texture IMG = TextureLoader.getTexture(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = TextureLoader.getTexture(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public CannonballsOfSteel() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    /* Logic is in CannonballsOfSteelApplyPowersPatch and CannonballsOfSteelCalculateCardDamagePatch. */

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
