package blackbeard.patches;

import blackbeard.powers.ParrotPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch(
        clz = GameActionManager.class,
        method = "callEndOfTurnActions"
)


public class ParrotPatch {

    public static void Prefix(GameActionManager gameActionManager) {
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof ParrotPower) {
                ParrotPower parrotPower = (ParrotPower) power;
                if (parrotPower.lastCardQueueItem != null) {
                    parrotPower.flash();
                    AbstractCard card = parrotPower.lastCardQueueItem.card;
                    card.freeToPlayOnce = true;
                    AbstractDungeon.player.limbo.addToBottom(card);
                    AbstractDungeon.actionManager.cardQueue.add(parrotPower.lastCardQueueItem);
                    if (card.cardID.equals("Rampage")) {
                        AbstractDungeon.actionManager.addToBottom(new ModifyDamageAction(card, card.magicNumber));
                    } else if (card.cardID.equals("Genetic Algorithm")) {
                        AbstractDungeon.actionManager.addToBottom(new IncreaseMiscAction(card.cardID, card.misc + card.magicNumber, card.magicNumber));
                    }
                }
            }
        }
    }

}
