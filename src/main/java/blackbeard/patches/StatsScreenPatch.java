package blackbeard.patches;

import blackbeard.achievements.BlackbeardAchievementGrid;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;

@SpirePatch(clz = StatsScreen.class, method = SpirePatch.CLASS)
public class StatsScreenPatch {
    private static BlackbeardAchievementGrid blackbeardAchievements;

    public static BlackbeardAchievementGrid getBlackbeardAchievements() {
        if (blackbeardAchievements == null) {
            blackbeardAchievements = new BlackbeardAchievementGrid();
        }
        return blackbeardAchievements;
    }
}