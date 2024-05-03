package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

public class Spearhead extends AbstractBlackbeardRelic {

    public static final String ID = "blackbeard:Spearhead";
    private static final Texture IMG = TextureLoader.getTexture(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = TextureLoader.getTexture(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public Spearhead() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    /* Logic is in AbstractWeaponOrb::getDamageToDeal. */

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
