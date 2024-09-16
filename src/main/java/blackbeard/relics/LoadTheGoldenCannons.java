package blackbeard.relics;

import basemod.helpers.CardPowerTip;
import blackbeard.TheBlackbeardMod;
import blackbeard.cards.GoldenCannonball;
import blackbeard.interfaces.IGoldenRelic;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import org.apache.commons.lang3.StringUtils;

import static blackbeard.TheBlackbeardMod.makeID;

public class LoadTheGoldenCannons extends AbstractBlackbeardRelic implements IGoldenRelic {

    public static final String ID = makeID("LoadTheGoldenCannons");
    private static final Texture IMG = TextureLoader.getTexture(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = TextureLoader.getTexture(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public LoadTheGoldenCannons() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
        refreshTips();
    }

    @Override
    public void atPreBattle() {
        addAGoldenCannonballToTheHand();
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
            addAGoldenCannonballToTheHand();
        }
    }

    private void addAGoldenCannonballToTheHand() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new GoldenCannonball(), false));
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(LoadTheCannons.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (StringUtils.equals(AbstractDungeon.player.relics.get(i).relicId, LoadTheCannons.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(LoadTheCannons.ID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onGainGold() {
        refreshTips();
    }

    public void refreshTips() {
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
        this.tips.add(new CardPowerTip(new GoldenCannonball()));
    }

    @Override
    public void updateGoldenValues() {
        refreshTips();
    }
}
