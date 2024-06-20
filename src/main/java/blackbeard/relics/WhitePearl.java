package blackbeard.relics;

import blackbeard.TheBlackbeardMod;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

public class WhitePearl extends AbstractBlackbeardRelic {

    public static final String ID = "blackbeard:WhitePearl";
    private static final Texture IMG = TextureLoader.getTexture(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = TextureLoader.getTexture(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public WhitePearl() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    /* Logic is in EquipAction. */

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
