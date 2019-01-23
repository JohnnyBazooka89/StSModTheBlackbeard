package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class TreasureChest extends CustomRelic {

    public static final String ID = "blackbeard:TreasureChest";
    public static final Texture IMG = ImageMaster.loadImage(TheBlackbeardMod.getRelicImagePath(ID));
    public static final Texture OUTLINE = ImageMaster.loadImage(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public TreasureChest() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onVictory() {
        this.flash();
        CardCrawlGame.sound.play("GOLD_GAIN");
        AbstractDungeon.player.gainGold(15);
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
