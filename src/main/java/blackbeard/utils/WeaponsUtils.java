package blackbeard.utils;

import blackbeard.orbs.AbstractWeaponOrb;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class WeaponsUtils {

    public static String getWeaponRawDescription(String description, int weaponAttack, int weaponDurability){
        return description.replace("{A}", Integer.toString(weaponAttack)).replace("{D}", Integer.toString(weaponDurability));
    }

    public static int getNumberOfWeapons() {
        int weaponCount = 0;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof AbstractWeaponOrb) {
                weaponCount++;
            }
        }
        return weaponCount;
    }

}
