package blackbeard.patches;

import blackbeard.TheBlackbeardMod;
import blackbeard.achievements.BlackbeardAchievementItem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;

@SpirePatch(clz = AchievementGrid.class, method = "<ctor>")
public class AchievementGridConstructorPatch {

    public AchievementGridConstructorPatch() {}

    @SpirePostfixPatch
    public static void Postfix(AchievementGrid instance) {
        BlackbeardAchievementItem.atlas = new TextureAtlas(Gdx.files.internal("blackbeard/img/achievements/BlackbeardAchievements.atlas"));
        loadAchievement(instance, "loadthecannons", TheBlackbeardMod.makeAchievementKey("LOAD_THE_CANNONS"), false);
        loadAchievement(instance, "armedtotheteeth", TheBlackbeardMod.makeAchievementKey("ARMED_TO_THE_TEETH"), false);
        loadAchievement(instance, "ultimateweapon", TheBlackbeardMod.makeAchievementKey("ULTIMATE_WEAPON"), false);
        loadAchievement(instance, "resistant", TheBlackbeardMod.makeAchievementKey("RESISTANT"), false);
        loadAchievement(instance, "riches", TheBlackbeardMod.makeAchievementKey("RICHES"), false);
        loadAchievement(instance, "blackbeardmastery", TheBlackbeardMod.makeAchievementKey("BLACKBEARD_MASTERY"), false);
    }

    private static void loadAchievement(AchievementGrid instance, String imgName, String id, boolean isHidden) {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(id);
        String name = uiStrings.TEXT[0];
        String description = uiStrings.TEXT[1];

        TextureAtlas.AtlasRegion AchievementImageUnlocked = BlackbeardAchievementItem.atlas.findRegion("unlocked/" + imgName);
        TextureAtlas.AtlasRegion AchievementImageLocked = BlackbeardAchievementItem.atlas.findRegion("locked/" + imgName);

        instance.items.add(new BlackbeardAchievementItem(name, description, id, isHidden, AchievementImageUnlocked, AchievementImageLocked));
    }
}