package blackbeard.relics;

import blackbeard.TheBlackbeardMod;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import static blackbeard.TheBlackbeardMod.makeID;

public class Spearhead extends AbstractBlackbeardRelic {

    public static final String ID = makeID("Spearhead");
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
