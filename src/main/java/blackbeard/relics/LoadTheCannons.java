package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import blackbeard.TheBlackbeardMod;
import blackbeard.cards.Cannonball;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;

public class LoadTheCannons extends AbstractBlackbeardRelic {

    public static final String ID = "blackbeard:LoadTheCannons";
    private static final Texture IMG = TextureLoader.getTexture(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = TextureLoader.getTexture(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public LoadTheCannons() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        refreshTips();
    }

    @Override
    public void atPreBattle() {
        addACannonballToTheHand();
        this.counter = -1;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public void atTurnStart() {
        this.counter++;
        if (this.counter == 4) {
            this.counter = 0;
            addACannonballToTheHand();
        }
    }

    private void addACannonballToTheHand() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Cannonball(), false));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void refreshTips() {
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
        this.tips.add(new CardPowerTip(new Cannonball()));
    }

}
