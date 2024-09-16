package blackbeard.relics;

import blackbeard.TheBlackbeardMod;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import static blackbeard.TheBlackbeardMod.makeID;

public class PoorMathSkills extends AbstractBlackbeardRelic {

    public static final String ID = makeID("PoorMathSkills");
    private static final Texture IMG = TextureLoader.getTexture(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = TextureLoader.getTexture(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public PoorMathSkills() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    /* Logic is in GoldenCardsUtils. */

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
