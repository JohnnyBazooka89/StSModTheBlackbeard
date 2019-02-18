package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class PoorMathSkills extends CustomRelic {

    public static final String ID = "blackbeard:PoorMathSkills";
    public static final Texture IMG = ImageMaster.loadImage(TheBlackbeardMod.getRelicImagePath(ID));
    public static final Texture OUTLINE = ImageMaster.loadImage(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public PoorMathSkills() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    /* Logic is in GoldenCardsUtils. */

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
