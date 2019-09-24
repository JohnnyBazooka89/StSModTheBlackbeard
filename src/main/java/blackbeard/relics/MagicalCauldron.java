package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Sozu;

public class MagicalCauldron extends CustomRelic {

    public static final String ID = "blackbeard:MagicalCauldron";
    private static final Texture IMG = TextureLoader.getTexture(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = TextureLoader.getTexture(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public MagicalCauldron() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onVictory() {
        this.flash();
        if (AbstractDungeon.player.hasRelic(Sozu.ID)) {
            AbstractDungeon.player.getRelic(Sozu.ID).flash();
        } else {
            AbstractDungeon.player.obtainPotion(AbstractDungeon.returnRandomPotion(true));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
