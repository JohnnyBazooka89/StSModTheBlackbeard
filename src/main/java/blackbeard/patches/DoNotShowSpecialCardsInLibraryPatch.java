package blackbeard.patches;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.EverythingFix;
import blackbeard.enums.CardColorEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(clz = EverythingFix.Initialize.class, method = "Insert", paramtypez = {Object.class})
public class DoNotShowSpecialCardsInLibraryPatch {

    public static void Postfix(Object object) {
        EverythingFix.Fields.cardGroupMap.get(CardColorEnum.BLACKBEARD_BLACK).group.removeIf(c -> c.rarity == AbstractCard.CardRarity.SPECIAL);
    }
}