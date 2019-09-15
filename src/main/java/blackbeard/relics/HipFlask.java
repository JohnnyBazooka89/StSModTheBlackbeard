package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import blackbeard.powers.ResistancePower;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HipFlask extends CustomRelic {

    public static final String ID = "blackbeard:HipFlask";
    private static final Texture IMG = TextureLoader.getTexture(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = TextureLoader.getTexture(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public HipFlask() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResistancePower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
