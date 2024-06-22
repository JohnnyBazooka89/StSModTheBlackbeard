package blackbeard.achievements;

import blackbeard.TheBlackbeardMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

import static blackbeard.TheBlackbeardMod.blackbeardAchievementItems;

public class BlackbeardAchievementGrid {
    public ArrayList<BlackbeardAchievementItem> items = new ArrayList<>();
    private static final float SPACING = 200.0F * Settings.scale;
    private static final int ITEMS_PER_ROW = 5;

    public BlackbeardAchievementGrid() {
        BlackbeardAchievementItem.atlas = new TextureAtlas(Gdx.files.internal("blackbeard/img/achievements/BlackbeardAchievements.atlas"));
        loadAchievement("LOAD_THE_CANNONS", false);
        loadAchievement("ARMED_TO_THE_TEETH", false);
        loadAchievement("ULTIMATE_WEAPON", false);
        loadAchievement("RESISTANT", false);
        loadAchievement("RICHES", false);
        loadAchievement("BLACKBEARD_MASTERY", false);
    }

    private void loadAchievement(String id, boolean isHidden) {
        String fullId = TheBlackbeardMod.makeAchievementKey(id);
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(fullId);
        String name = uiStrings.TEXT[0];
        String description = uiStrings.TEXT[1];
        TextureAtlas.AtlasRegion AchievementImageUnlocked = BlackbeardAchievementItem.atlas.findRegion("unlocked/" + id);
        TextureAtlas.AtlasRegion AchievementImageLocked = BlackbeardAchievementItem.atlas.findRegion("locked/" + id);

        items.add(new BlackbeardAchievementItem(name, description, fullId, isHidden, AchievementImageUnlocked, AchievementImageLocked));
        blackbeardAchievementItems.put(fullId, new BlackbeardAchievementItem(name, description, fullId, isHidden, AchievementImageUnlocked, AchievementImageLocked));
    }

    public void updateAchievementStatus() {
        for (BlackbeardAchievementItem item : items) {
            String achievementKey = item.getKey();
            boolean isUnlocked = UnlockTracker.isAchievementUnlocked(achievementKey);
            item.isUnlocked = isUnlocked;
            item.reloadImg();
        }
    }

    public void render(SpriteBatch sb, float renderY) {
        for (int i = 0; i < items.size(); ++i) {
            items.get(i).render(sb, 560.0F * Settings.scale + (i % ITEMS_PER_ROW) * SPACING, renderY - (i / ITEMS_PER_ROW) * SPACING + 680.0F * Settings.yScale);
        }
    }

    public float calculateHeight() {
        int numRows = (items.size() + ITEMS_PER_ROW - 1) / ITEMS_PER_ROW;
        return numRows * SPACING + 50.0F * Settings.scale;
    }

    public void update() {
        updateAchievementStatus();
        for (BlackbeardAchievementItem item : items) {
            item.update();
        }
    }

}