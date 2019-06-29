package blackbeard.patches;

import blackbeard.enums.CardColorEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

@SpirePatch(clz = CardLibrary.class, method = "getCardList", paramtypez = {CardLibrary.LibraryType.class})
public class DoNotShowSpecialCardsInLibraryPatch {

    public static ArrayList<AbstractCard> Postfix(ArrayList<AbstractCard> retVal, CardLibrary.LibraryType type) {
        retVal.removeIf(c -> c.color == CardColorEnum.BLACKBEARD_BLACK && c.rarity == AbstractCard.CardRarity.SPECIAL);
        return retVal;
    }
}