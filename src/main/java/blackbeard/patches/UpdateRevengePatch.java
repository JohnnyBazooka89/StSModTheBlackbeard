package blackbeard.patches;

import blackbeard.cards.Revenge;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = AbstractPlayer.class, method = "damage", paramtypez = {DamageInfo.class})
@SpirePatch(clz = AbstractPlayer.class, method = "heal", paramtypez = {int.class})

public class UpdateRevengePatch {

    public static void Postfix(AbstractPlayer abstractPlayer, DamageInfo info) {
        updateRevenge();
    }

    public static void Postfix(AbstractPlayer abstractPlayer, int amount) {
        updateRevenge();
    }

    private static void updateRevenge() {
        updateRevengeInCardGroup(AbstractDungeon.player.masterDeck);
        updateRevengeInCardGroup(AbstractDungeon.player.hand);
        updateRevengeInCardGroup(AbstractDungeon.player.drawPile);
        updateRevengeInCardGroup(AbstractDungeon.player.discardPile);
        updateRevengeInCardGroup(AbstractDungeon.player.exhaustPile);
        updateRevengeInCardGroup(AbstractDungeon.player.limbo);
    }

    private static void updateRevengeInCardGroup(CardGroup cardGroup) {

        for (AbstractCard card : cardGroup.group) {
            if (card instanceof Revenge) {
                ((Revenge) card).setBaseDamageAndUpgradeDescription();
            }
        }
    }

}
