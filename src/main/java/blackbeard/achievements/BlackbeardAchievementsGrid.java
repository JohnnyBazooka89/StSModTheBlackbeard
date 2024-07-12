package blackbeard.achievements;

import blackbeard.TheBlackbeardMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.LinkedHashMap;
import java.util.Map;

public class BlackbeardAchievementsGrid {
    public Map<String, BlackbeardAchievementItem> items = new LinkedHashMap<>();
    private static final float SPACING = 200.0F * Settings.scale;
    private static final int ITEMS_PER_ROW = 5;

    public BlackbeardAchievementsGrid() {
        BlackbeardAchievementItem.atlas = new TextureAtlas(Gdx.files.internal("blackbeard/img/achievements/BlackbeardAchievements.atlas"));
        loadAchievement("ARMED_TO_THE_TEETH");
        loadAchievement("ULTIMATE_WEAPON");
        loadAchievement("LOAD_THE_CANNONS");
        loadAchievement("RESISTANT");
        loadAchievement("RICHES");
        loadAchievement("BLACKBEARD_MASTERY");
    }

    private void loadAchievement(String id) {
        String fullId = TheBlackbeardMod.makeAchievementKey(id);
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(fullId);
        String name = uiStrings.TEXT[0];
        String description = uiStrings.TEXT[1];
        TextureAtlas.AtlasRegion AchievementImageUnlocked = BlackbeardAchievementItem.atlas.findRegion("unlocked/" + id);
        TextureAtlas.AtlasRegion AchievementImageLocked = BlackbeardAchievementItem.atlas.findRegion("locked/" + id);

        items.put(fullId, new BlackbeardAchievementItem(name, description, fullId, AchievementImageUnlocked, AchievementImageLocked));
    }

    public void updateAchievementStatus() {
        for (BlackbeardAchievementItem item : items.values()) {
            String achievementKey = item.getKey();
            boolean isUnlocked = UnlockTracker.isAchievementUnlocked(achievementKey);
            item.isUnlocked = isUnlocked;
            item.reloadImg();
        }
    }

    public void render(SpriteBatch sb, float renderY) {
        int i = 0;
        for (BlackbeardAchievementItem item : items.values()) {
            item.render(sb, 560.0F * Settings.scale + (i % ITEMS_PER_ROW) * SPACING, renderY - (i / ITEMS_PER_ROW) * SPACING + 680.0F * Settings.yScale);
            i++;
        }
    }

    public float calculateHeight() {
        int numRows = (items.size() + ITEMS_PER_ROW - 1) / ITEMS_PER_ROW;
        return numRows * SPACING + 50.0F * Settings.scale;
    }

    public void update() {
        updateAchievementStatus();
        for (BlackbeardAchievementItem item : items.values()) {
            item.update();
        }
    }

}