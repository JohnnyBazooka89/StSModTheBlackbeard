package blackbeard.patches;

import basemod.ReflectionHacks;
import blackbeard.TheBlackbeardMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;

@SpirePatch2(
        clz = StatsScreen.class,
        method = "calculateScrollBounds"
)
public class StatsScreenScrollbarPatch {
    @SpirePostfixPatch
    public static void Postfix(StatsScreen __instance) {
        float currentUpperBound = ReflectionHacks.getPrivate(__instance, StatsScreen.class, "scrollUpperBound");
        float blackbeardAchievementsHeight = TheBlackbeardMod.blackbeardAchievementsGrid.calculateHeight();
        ReflectionHacks.setPrivate(__instance, StatsScreen.class, "scrollUpperBound", currentUpperBound + (blackbeardAchievementsHeight + (50F * Settings.scale)));
    }
}