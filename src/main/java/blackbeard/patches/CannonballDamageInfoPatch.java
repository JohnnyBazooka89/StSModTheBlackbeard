package blackbeard.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;

@SpirePatch(clz = DamageInfo.class, method = SpirePatch.CLASS)
public class CannonballDamageInfoPatch {
    public static SpireField<Boolean> cannonballDamageInfo = new SpireField<>(() -> false);
}
