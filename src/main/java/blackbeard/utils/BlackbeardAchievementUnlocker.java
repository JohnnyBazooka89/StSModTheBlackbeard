package blackbeard.utils;

import blackbeard.TheBlackbeardMod;
import com.megacrit.cardcrawl.core.Settings;

import static com.megacrit.cardcrawl.unlock.UnlockTracker.achievementPref;

public class BlackbeardAchievementUnlocker {
    public static void unlockAchievement(String key) {
        if (!Settings.isShowBuild && Settings.isStandardRun()) {
            if (!achievementPref.getBoolean(key, false)) {
                achievementPref.putBoolean(key, true);
                achievementPref.flush();
                TheBlackbeardMod.customAchievementPopupRenderer.addAchievementToRenderQueue(TheBlackbeardMod.blackbeardAchievementItems.get(key));
            }
        }
    }
}