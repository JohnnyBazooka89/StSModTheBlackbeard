package blackbeard.patches;

import blackbeard.interfaces.IGoldenCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(clz = AbstractPlayer.class, method = "gainGold")
public class GoldenCardsFixAbstractPlayerPatch {

    public static void Postfix(AbstractPlayer abstractPlayer, int amount) {
        refreshGoldenCardsInCardGroup(abstractPlayer.masterDeck);
        refreshGoldenCardsInCardGroup(abstractPlayer.drawPile);
        refreshGoldenCardsInCardGroup(abstractPlayer.hand);
        refreshGoldenCardsInCardGroup(abstractPlayer.discardPile);
        refreshGoldenCardsInCardGroup(abstractPlayer.exhaustPile);
        refreshGoldenCardsInCardGroup(abstractPlayer.limbo);
    }

    private static void refreshGoldenCardsInCardGroup(CardGroup cardGroup) {
        for (AbstractCard card : cardGroup.group) {
            if (card instanceof IGoldenCard) {
                IGoldenCard goldenCard = (IGoldenCard) card;
                goldenCard.setGoldenValuesAndInitializeDescription();
            }
        }
    }

}
