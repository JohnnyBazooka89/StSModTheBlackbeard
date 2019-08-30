package blackbeard.patches;

import blackbeard.powers.ParrotPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = AbstractDungeon.class, method = "resetPlayer")
public class RemoveParrotOnResetPlayerPatch {

    public static void Postfix() {
        ParrotPower.removeParrotModelFromCharacter();
    }

}
