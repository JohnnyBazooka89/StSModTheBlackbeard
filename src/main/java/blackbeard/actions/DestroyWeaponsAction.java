package blackbeard.actions;

import blackbeard.enums.WeaponsToUseEnum;
import blackbeard.orbs.WeaponOrb;
import blackbeard.powers.WeaponPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;


public class DestroyWeaponsAction extends AbstractGameAction {
    private WeaponsToUseEnum weaponsToUse;

    public DestroyWeaponsAction(WeaponsToUseEnum weaponsToUse) {
        this.weaponsToUse = weaponsToUse;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                if (orb instanceof WeaponOrb) {
                    WeaponOrb weaponOrb = (WeaponOrb) orb;
                    weaponOrb.setDurabilityToZero();
                    if (weaponsToUse.equals(WeaponsToUseEnum.ONLY_RIGHTMOST_WEAPON)) {
                        break;
                    }
                }
            }

            if (AbstractDungeon.player.hasPower(WeaponPower.POWER_ID)) {
                WeaponPower weaponPower = (WeaponPower) AbstractDungeon.player.getPower(WeaponPower.POWER_ID);
                weaponPower.refreshWeapons();
            }

            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }

        this.tickDuration();
    }
}
