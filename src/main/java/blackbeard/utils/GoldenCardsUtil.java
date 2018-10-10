package blackbeard.utils;

import com.megacrit.cardcrawl.core.CardCrawlGame;

public class GoldenCardsUtil {

    public static String getGoldenCardDescription(String description, String[] extendedDescription) {
        return description + extendedDescription[1] + CardCrawlGame.goldGained + extendedDescription[2];
    }

}
