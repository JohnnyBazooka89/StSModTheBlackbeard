package blackbeard.utils;

import blackbeard.relics.PoorMathSkills;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GoldenCardsUtil {

    public static String getGoldenCardDescription(boolean upgraded, String description, String upgradeDescription, String[] extendedDescription) {
        String result = upgraded ? upgradeDescription : description;
        if (CardCrawlGame.isInARun()) {
            result += extendedDescription[0] + extendedDescription[1] + getBlackbeardGoldGained() + extendedDescription[2] + " ";
        }
        return result;
    }

    public static int getBlackbeardGoldGained() {
        int additionalFakeGoldGained = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(PoorMathSkills.ID)) {
            additionalFakeGoldGained += 500;
        }
        return CardCrawlGame.goldGained + additionalFakeGoldGained;
    }
}
