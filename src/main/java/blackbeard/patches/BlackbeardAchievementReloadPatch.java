package blackbeard.patches;

import blackbeard.achievements.BlackbeardAchievementItem;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.screens.stats.AchievementItem;

@SpirePatch(
        clz = AchievementItem.class,
        method = "reloadImg"
)
public class BlackbeardAchievementReloadPatch {
    public BlackbeardAchievementReloadPatch() {
    }

    @SpirePostfixPatch
    public static void Postfix(AchievementItem __instance) {
        if (__instance instanceof BlackbeardAchievementItem) {
            ((BlackbeardAchievementItem)__instance).currentImg = BlackbeardAchievementItem.atlas.findRegion(((BlackbeardAchievementItem)__instance).currentImg.name);
        }

    }
}
