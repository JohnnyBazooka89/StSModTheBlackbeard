package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

public class KarateGi extends CustomRelic {

    public static final String ID = "blackbeard:KarateGi";
    private static final Texture IMG = TextureLoader.getTexture(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = TextureLoader.getTexture(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public KarateGi() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
