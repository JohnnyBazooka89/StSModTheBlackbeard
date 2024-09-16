package blackbeard.relics;

import blackbeard.TheBlackbeardMod;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static blackbeard.TheBlackbeardMod.makeID;

public class MagicalCauldron extends AbstractBlackbeardRelic {

    public static final String ID = makeID("MagicalCauldron");
    private static final Texture IMG = TextureLoader.getTexture(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = TextureLoader.getTexture(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public MagicalCauldron() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onVictory() {
        this.flash();
        ObtainPotionAction obtainPotionAction = new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true));
        obtainPotionAction.update();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
