package blackbeard.patches;

import blackbeard.cards.Revenge;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UpdateRevengePatches {

    @SpirePatch(clz = AbstractPlayer.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class DamagePatch {
        public static void Postfix(AbstractPlayer abstractPlayer, DamageInfo info) {
            updateRevenge();
        }
    }

    @SpirePatch(clz = AbstractCreature.class, method = "heal", paramtypez = {int.class, boolean.class})
    public static class HealPatch {
        public static void Postfix(AbstractCreature abstractCreature, int healAmount, boolean showEffect) {
            updateRevenge();
        }
    }

    private static void updateRevenge() {
        updateRevengeInCardGroup(AbstractDungeon.player.masterDeck);
        updateRevengeInCardGroup(AbstractDungeon.player.hand);
        updateRevengeInCardGroup(AbstractDungeon.player.drawPile);
        updateRevengeInCardGroup(AbstractDungeon.player.discardPile);
        updateRevengeInCardGroup(AbstractDungeon.player.exhaustPile);
        updateRevengeInCardGroup(AbstractDungeon.player.limbo);

        updateRevengeInCardGroup(AbstractDungeon.srcColorlessCardPool);
        updateRevengeInCardGroup(AbstractDungeon.srcCommonCardPool);
        updateRevengeInCardGroup(AbstractDungeon.srcCurseCardPool);
        updateRevengeInCardGroup(AbstractDungeon.srcRareCardPool);
        updateRevengeInCardGroup(AbstractDungeon.srcUncommonCardPool);

        updateRevengeInCardGroup(AbstractDungeon.colorlessCardPool);
        updateRevengeInCardGroup(AbstractDungeon.commonCardPool);
        updateRevengeInCardGroup(AbstractDungeon.curseCardPool);
        updateRevengeInCardGroup(AbstractDungeon.rareCardPool);
        updateRevengeInCardGroup(AbstractDungeon.uncommonCardPool);
    }

    private static void updateRevengeInCardGroup(CardGroup cardGroup) {

        for (AbstractCard card : cardGroup.group) {
            if (card instanceof Revenge) {
                ((Revenge) card).setBaseDamageAndUpdateDescription();
            }
        }
    }

}
