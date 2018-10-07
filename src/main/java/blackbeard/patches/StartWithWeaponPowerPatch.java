package blackbeard.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "applyStartOfCombatLogic"
)


public class StartWithWeaponPowerPatch {

    public static void Postfix(AbstractPlayer abstractPlayer) {
        //abstractPlayer.addPower(new WeaponPower(abstractPlayer));

        //No more needed - Changed to create this power dynamically.
    }

}
