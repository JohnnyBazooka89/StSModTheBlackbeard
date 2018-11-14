package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import blackbeard.cards.Cannonball;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoadTheCannons extends CustomRelic {
    public static final String ID = "blackbeard:LoadTheCannons";

    public LoadTheCannons() {
        super(ID, new Texture(TheBlackbeardMod.getRelicImagePath(ID)), new Texture(TheBlackbeardMod.getRelicOutlineImagePath(ID)), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Cannonball(), false));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
