package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import com.badlogic.gdx.graphics.Texture;

public class PoorMathSkills extends CustomRelic {
    public static final String ID = "blackbeard:PoorMathSkills";

    public PoorMathSkills() {
        super(ID, new Texture(TheBlackbeardMod.getRelicImagePath(ID)), RelicTier.RARE, LandingSound.MAGICAL);
    }

    /* Logic is in GoldenCardsUtil. */

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
