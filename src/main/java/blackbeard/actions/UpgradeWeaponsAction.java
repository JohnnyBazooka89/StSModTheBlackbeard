package blackbeard.actions;

import blackbeard.enums.WeaponsToUseEnum;
import blackbeard.orbs.AbstractWeaponOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;


public class UpgradeWeaponsAction extends AbstractGameAction {
    private int attackUpgrade;
    private int durabilityUpgrade;
    private WeaponsToUseEnum weaponsToUse;

    public UpgradeWeaponsAction(int attackUpgrade, int durabilityUpgrade, WeaponsToUseEnum weaponsToUse) {
        this.attackUpgrade = attackUpgrade;
        this.durabilityUpgrade = durabilityUpgrade;
        this.weaponsToUse = weaponsToUse;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                if (orb instanceof AbstractWeaponOrb) {
                    AbstractWeaponOrb weaponOrb = (AbstractWeaponOrb) orb;
                    weaponOrb.upgrade(attackUpgrade, durabilityUpgrade);
                    if (weaponsToUse.equals(WeaponsToUseEnum.ONLY_RIGHTMOST_WEAPON)) {
                        break;
                    }
                }
            }

            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }

        this.tickDuration();
    }
}
