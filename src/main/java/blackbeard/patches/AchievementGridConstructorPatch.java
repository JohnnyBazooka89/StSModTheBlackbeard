package blackbeard.patches;

import blackbeard.TheBlackbeardMod;
import blackbeard.achievements.BlackbeardAchievementItem;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;

import java.util.Map;

@SpirePatch(clz = AchievementGrid.class, method = "<ctor>")
public class AchievementGridConstructorPatch {

    public AchievementGridConstructorPatch() {}

    @SpirePostfixPatch
    public static void Postfix(AchievementGrid instance) {
        for(Map.Entry<String, BlackbeardAchievementItem> entry : TheBlackbeardMod.blackbeardAchievementItems.entrySet()) {
            instance.items.add(entry.getValue());
        }
    }

}