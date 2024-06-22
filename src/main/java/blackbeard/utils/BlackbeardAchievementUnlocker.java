package blackbeard.utils;

import basemod.BaseMod;
import blackbeard.TheBlackbeardMod;
import blackbeard.achievements.BlackbeardAchievementItem;
import com.megacrit.cardcrawl.core.Settings;

import static com.megacrit.cardcrawl.unlock.UnlockTracker.achievementPref;

public class BlackbeardAchievementUnlocker {
    public static void unlockAchievement(String key) {
        String fullKey = TheBlackbeardMod.makeAchievementKey(key);
        BaseMod.logger.info("Attempting to unlock achievement with key: {}", fullKey);

        if (!Settings.isShowBuild && Settings.isStandardRun()) {
            if (!achievementPref.getBoolean(fullKey, false)) {
                achievementPref.putBoolean(fullKey, true);
                achievementPref.flush();

                BlackbeardAchievementItem achievement = TheBlackbeardMod.blackbeardAchievementItems.get(fullKey);
                if (achievement == null) {
                    BaseMod.logger.error("Achievement not found for key: {}", fullKey);
                    return;
                }

                BaseMod.logger.info("Unlocked achievement: {}", fullKey);
                TheBlackbeardMod.customAchievementPopupRenderer.addAchievementToRenderQueue(achievement);
            }
        }
    }
}