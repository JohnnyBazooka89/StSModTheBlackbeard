package blackbeard.actions;

import blackbeard.effects.UnconditionalWaitAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;


public class GoldenRainAction extends AbstractGameAction {

    private DamageAllEnemiesAction damageAllEnemiesAction;

    public GoldenRainAction(DamageAllEnemiesAction damageAllEnemiesAction) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.damageAllEnemiesAction = damageAllEnemiesAction;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new RainingGoldEffect(50)));
            AbstractDungeon.actionManager.addToBottom(new UnconditionalWaitAction(2.5F));
            AbstractDungeon.actionManager.addToBottom(damageAllEnemiesAction);
        }

        this.tickDuration();
    }
}
