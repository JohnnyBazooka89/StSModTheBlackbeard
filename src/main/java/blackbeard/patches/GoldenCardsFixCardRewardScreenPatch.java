package blackbeard.patches;

import blackbeard.interfaces.IGoldenCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.CardRewardScreen;

@SpirePatch(clz = CardRewardScreen.class, method = "update")
public class GoldenCardsFixCardRewardScreenPatch {

    public static void Postfix(CardRewardScreen cardRewardScreen) {
        for (AbstractCard card : cardRewardScreen.rewardGroup) {
            if (card instanceof IGoldenCard) {
                IGoldenCard goldenCard = (IGoldenCard) card;
                goldenCard.setGoldenValuesAndUpdateDescription();
            }
        }
    }

}
