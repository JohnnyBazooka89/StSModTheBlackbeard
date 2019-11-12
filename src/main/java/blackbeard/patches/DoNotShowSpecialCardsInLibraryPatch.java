package blackbeard.patches;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.EverythingFix;
import blackbeard.enums.CardColorEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;

@SpirePatch(clz = CardLibraryScreen.class, method = "initialize", paramtypez = {})
public class DoNotShowSpecialCardsInLibraryPatch {

    public static void Postfix(CardLibraryScreen cardLibraryScreen) {
        EverythingFix.Fields.cardGroupMap.get(CardColorEnum.BLACKBEARD_BLACK).group.removeIf(c -> c.rarity == AbstractCard.CardRarity.SPECIAL);
    }
}