package blackbeard.actions;

import blackbeard.TheBlackbeardMod;
import blackbeard.cards.AgileStrike;
import blackbeard.characters.TheBlackbeard;
import blackbeard.orbs.AbstractWeaponOrb;
import blackbeard.powers.WeaponPower;
import blackbeard.relics.WhitePearl;
import blackbeard.utils.BlackbeardAchievementUnlocker;
import blackbeard.utils.WeaponsUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class EquipAction extends AbstractGameAction {
    private AbstractWeaponOrb weaponOrb;

    public EquipAction(AbstractWeaponOrb weaponOrb) {
        this.weaponOrb = weaponOrb;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public static boolean isInEquipActionWhileIncreasingMaxOrbSlots = false;

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            isInEquipActionWhileIncreasingMaxOrbSlots = true;
            AbstractDungeon.player.increaseMaxOrbSlots(1, true);
            isInEquipActionWhileIncreasingMaxOrbSlots = false;

            if (AbstractDungeon.player.hasRelic(WhitePearl.ID)) {
                weaponOrb.upgrade(1, 0);
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, AbstractDungeon.player.getRelic(WhitePearl.ID)));
            }

            AbstractDungeon.player.channelOrb(weaponOrb);
            AbstractDungeon.player.addPower(new WeaponPower(AbstractDungeon.player));
            WeaponPower weaponPower = (WeaponPower) AbstractDungeon.player.getPower(WeaponPower.POWER_ID);
            weaponPower.refreshWeapons();

            reduceAgileStrikesCostForTurn();

            if (WeaponsUtils.getNumberOfWeapons() >= 10) {
                AbstractPlayer p = AbstractDungeon.player;
                if (p instanceof TheBlackbeard) {
                    BlackbeardAchievementUnlocker.unlockAchievement("ARMED_TO_THE_TEETH");
                }
            }

            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }

        this.tickDuration();
    }

    private void reduceAgileStrikesCostForTurn() {
        reduceAgileStrikesCostForTurnInCardGroup(AbstractDungeon.player.hand);
        reduceAgileStrikesCostForTurnInCardGroup(AbstractDungeon.player.drawPile);
        reduceAgileStrikesCostForTurnInCardGroup(AbstractDungeon.player.discardPile);
        reduceAgileStrikesCostForTurnInCardGroup(AbstractDungeon.player.exhaustPile);
        reduceAgileStrikesCostForTurnInCardGroup(AbstractDungeon.player.limbo);
    }

    private static void reduceAgileStrikesCostForTurnInCardGroup(CardGroup cardGroup) {
        for (AbstractCard card : cardGroup.group) {
            if (card instanceof AgileStrike) {
                card.setCostForTurn(0);
            }
        }
    }
}
