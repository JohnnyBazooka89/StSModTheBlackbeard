package blackbeard.utils;

public class WeaponCardsUtils {

    public static String getWeaponRawDescription(String description, int weaponAttack, int weaponDurability){
        return description.replace("{A}", Integer.toString(weaponAttack)).replace("{D}", Integer.toString(weaponDurability));
    }

}
