package blackbeard.utils;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import static com.megacrit.cardcrawl.core.Settings.GameLanguage.POL;
import static com.megacrit.cardcrawl.core.Settings.GameLanguage.RUS;

public class KeywordsUtils {
    public static void removeExhaustFromTooltips(AbstractCard card) {
        if (Settings.language == POL) {
            card.keywords.removeIf(k -> k.equalsIgnoreCase("wykluczenie"));
        } else if (Settings.language == RUS) {
            card.keywords.removeIf(k -> k.equalsIgnoreCase("сжигание"));
        } else {
            card.keywords.removeIf(k -> k.equalsIgnoreCase("exhaust"));
        }
    }
}
