package blackbeard.actions;

import blackbeard.orbs.DaggerOrb;
import blackbeard.powers.WeaponPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DaggerAction extends AbstractGameAction {

    private AbstractPlayer p;
    private DaggerOrb daggerOrb;

    public DaggerAction(DaggerOrb daggerOrb) {
        this.actionType = ActionType.DAMAGE;
        this.p = AbstractDungeon.player;
        this.daggerOrb = daggerOrb;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            AbstractDungeon.actionManager.addToBottom(new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster(true), new DamageInfo(p, daggerOrb.getDamageToDeal(), DamageInfo.DamageType.THORNS), 1));

            daggerOrb.use();

            if (AbstractDungeon.player.hasPower(WeaponPower.POWER_ID)) {
                WeaponPower weaponPower = (WeaponPower) AbstractDungeon.player.getPower(WeaponPower.POWER_ID);
                weaponPower.destroyWeaponsWithNoDurability();
            }

            this.isDone = true;
        } else {
            this.tickDuration();
        }
    }

}
