package blackbeard.actions;

import blackbeard.utils.GoldenCardsUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;


public class GoldenRainAction extends AbstractGameAction {

    private DamageAllEnemiesAction damageAllEnemiesAction;

    public GoldenRainAction(DamageAllEnemiesAction damageAllEnemiesAction) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.damageAllEnemiesAction = damageAllEnemiesAction;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new RainingGoldEffect(GoldenCardsUtils.getBlackbeardGoldGained() / 10)));
            AbstractDungeon.actionManager.addToBottom(new UnconditionalWaitAction(2.5F));
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDying && !mo.isDead) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(mo.hb.cX, mo.hb.cY, true)));
                }
            }
            AbstractDungeon.actionManager.addToBottom(damageAllEnemiesAction);
        }

        this.tickDuration();
    }
}
