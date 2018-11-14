package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class GoldenRing extends CustomRelic {
    public static final String ID = "blackbeard:GoldenRing";

    public GoldenRing() {
        super(ID, new Texture(TheBlackbeardMod.getRelicImagePath(ID)), new Texture(TheBlackbeardMod.getRelicOutlineImagePath(ID)), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
