package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class Spearhead extends CustomRelic {

    public static final String ID = "blackbeard:Spearhead";
    private static final Texture IMG = ImageMaster.loadImage(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = ImageMaster.loadImage(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public Spearhead() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    /* Logic is in WeaponOrb::getDamageToDeal. */

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
