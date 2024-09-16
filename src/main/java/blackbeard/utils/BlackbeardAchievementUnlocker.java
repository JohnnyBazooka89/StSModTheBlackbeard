package blackbeard.utils;

import blackbeard.TheBlackbeardMod;
import blackbeard.achievements.BlackbeardAchievementItem;
import com.megacrit.cardcrawl.core.Settings;

import static blackbeard.TheBlackbeardMod.makeID;
import static com.megacrit.cardcrawl.unlock.UnlockTracker.achievementPref;

public class BlackbeardAchievementUnlocker {
    public static void unlockAchievement(String key) {
        String fullKey = makeID(key);

        if (!Settings.isShowBuild && Settings.isStandardRun()) {
            if (!achievementPref.getBoolean(fullKey, false)) {
                achievementPref.putBoolean(fullKey, true);
                achievementPref.flush();

                BlackbeardAchievementItem achievement = TheBlackbeardMod.blackbeardAchievementsGrid.items.get(fullKey);
                if (achievement == null) {
                    return;
                }

                TheBlackbeardMod.customAchievementPopupRenderer.addAchievementToRenderQueue(achievement);
            }
        }
    }
}