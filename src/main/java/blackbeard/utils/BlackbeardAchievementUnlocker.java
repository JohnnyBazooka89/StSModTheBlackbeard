package blackbeard.utils;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;

import static com.megacrit.cardcrawl.unlock.UnlockTracker.achievementPref;

public class BlackbeardAchievementUnlocker {
    public static void unlockAchievement(String key) {
        if (!Settings.isShowBuild && Settings.isStandardRun()) {
            CardCrawlGame.publisherIntegration.unlockAchievement(key);
            if (!achievementPref.getBoolean(key, false)) {
                achievementPref.putBoolean(key, true);
            }

            achievementPref.flush();
        }
    }
}