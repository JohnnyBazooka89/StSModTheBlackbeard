package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import com.badlogic.gdx.graphics.Texture;

public class WhitePearl extends CustomRelic {
    public static final String ID = "blackbeard:WhitePearl";

    public WhitePearl() {
        super(ID, new Texture(TheBlackbeardMod.getRelicImagePath(ID)), new Texture(TheBlackbeardMod.getRelicOutlineImagePath(ID)), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    /* Logic is in EquipAction. */

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
