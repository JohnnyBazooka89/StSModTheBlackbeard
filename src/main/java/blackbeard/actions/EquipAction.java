package blackbeard.actions;

import blackbeard.orbs.WeaponOrb;
import blackbeard.powers.WeaponPower;
import blackbeard.relics.WhitePearl;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;


public class EquipAction extends AbstractGameAction {
    private WeaponOrb weaponOrb;

    public EquipAction(WeaponOrb weaponOrb) {
        this.weaponOrb = weaponOrb;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            boolean needToAddOrbSlot = true;

            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof EmptyOrbSlot) {
                    needToAddOrbSlot = false;
                    break;
                }
            }

            if (needToAddOrbSlot) {
                AbstractDungeon.player.increaseMaxOrbSlots(1, true);
            }

            if (AbstractDungeon.player.hasRelic(WhitePearl.ID)) {
                weaponOrb.upgrade(1, 0);
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, AbstractDungeon.player.getRelic(WhitePearl.ID)));
            }

            AbstractDungeon.player.channelOrb(weaponOrb);

            AbstractDungeon.player.addPower(new WeaponPower(AbstractDungeon.player));

            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }

        this.tickDuration();
    }

}
