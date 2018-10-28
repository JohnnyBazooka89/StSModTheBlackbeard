package blackbeard.utils;

import com.megacrit.cardcrawl.core.CardCrawlGame;

public class GoldenCardsUtil {

    public static String getGoldenCardDescription(boolean upgraded, String description, String upgradeDescription, String[] extendedDescription) {
        String result = upgraded ? upgradeDescription : description;
        if (CardCrawlGame.isInARun()) {
            result += extendedDescription[0] + extendedDescription[1] + CardCrawlGame.goldGained + extendedDescription[2] + " ";
        }
        return result;
    }

}
