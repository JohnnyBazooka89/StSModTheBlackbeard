package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class GoldenRing extends CustomRelic {

    public static final String ID = "blackbeard:GoldenRing";
    private static final Texture IMG = TextureLoader.getTexture(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = TextureLoader.getTexture(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public GoldenRing() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
