package blackbeard.patches;

import blackbeard.interfaces.IGoldenCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;

public class GoldenCardsFixPatches {

    @SpirePatch(clz = AbstractPlayer.class, method = "gainGold")
    public static class GoldenCardsFixAbstractPlayerPatch {

        public static void Postfix(AbstractPlayer abstractPlayer, int amount) {
            refreshGoldenCardsInCardGroup(abstractPlayer.masterDeck);
            refreshGoldenCardsInCardGroup(abstractPlayer.drawPile);
            refreshGoldenCardsInCardGroup(abstractPlayer.hand);
            refreshGoldenCardsInCardGroup(abstractPlayer.discardPile);
            refreshGoldenCardsInCardGroup(abstractPlayer.exhaustPile);
            refreshGoldenCardsInCardGroup(abstractPlayer.limbo);

            refreshGoldenCardsInCardGroup(AbstractDungeon.srcColorlessCardPool);
            refreshGoldenCardsInCardGroup(AbstractDungeon.srcCommonCardPool);
            refreshGoldenCardsInCardGroup(AbstractDungeon.srcCurseCardPool);
            refreshGoldenCardsInCardGroup(AbstractDungeon.srcRareCardPool);
            refreshGoldenCardsInCardGroup(AbstractDungeon.srcUncommonCardPool);

            refreshGoldenCardsInCardGroup(AbstractDungeon.colorlessCardPool);
            refreshGoldenCardsInCardGroup(AbstractDungeon.commonCardPool);
            refreshGoldenCardsInCardGroup(AbstractDungeon.curseCardPool);
            refreshGoldenCardsInCardGroup(AbstractDungeon.rareCardPool);
            refreshGoldenCardsInCardGroup(AbstractDungeon.uncommonCardPool);
        }

        private static void refreshGoldenCardsInCardGroup(CardGroup cardGroup) {
            for (AbstractCard card : cardGroup.group) {
                if (card instanceof IGoldenCard) {
                    IGoldenCard goldenCard = (IGoldenCard) card;
                    goldenCard.setGoldenValuesAndUpdateDescription();
                }
            }
        }

    }

    @SpirePatch(clz = CardRewardScreen.class, method = "update")
    public static class GoldenCardsFixCardRewardScreenPatch {

        public static void Postfix(CardRewardScreen cardRewardScreen) {
            for (AbstractCard card : cardRewardScreen.rewardGroup) {
                if (card instanceof IGoldenCard) {
                    IGoldenCard goldenCard = (IGoldenCard) card;
                    goldenCard.setGoldenValuesAndUpdateDescription();
                }
            }
        }

    }

}
