package blackbeard.actions;

import blackbeard.powers.DamageWhenIntangibleEndsPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class DamageWhenIntangibleEndsAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int damageAmount;

    public DamageWhenIntangibleEndsAction(int damageAmount) {
        this.actionType = ActionType.DAMAGE;
        this.p = AbstractDungeon.player;
        this.damageAmount = damageAmount;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (!AbstractDungeon.player.hasPower(IntangiblePlayerPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, damageAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, DamageWhenIntangibleEndsPower.POWER_ID));
            }

            this.isDone = true;
        } else {
            this.tickDuration();
        }
    }

}
