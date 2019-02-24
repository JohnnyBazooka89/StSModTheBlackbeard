package blackbeard.actions;

import blackbeard.orbs.SmithingHammerOrb;
import blackbeard.powers.WeaponPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class SmithingHammerAction extends AbstractGameAction {

    private AbstractPlayer p;
    private SmithingHammerOrb smithingHammerOrb;

    public SmithingHammerAction(SmithingHammerOrb smithingHammerOrb) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.smithingHammerOrb = smithingHammerOrb;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.group.isEmpty()) {
                this.isDone = true;
            } else {
                CardGroup upgradeable = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                Iterator var2 = this.p.hand.group.iterator();

                while (var2.hasNext()) {
                    AbstractCard c = (AbstractCard) var2.next();
                    if (c.canUpgrade() && c.type != AbstractCard.CardType.STATUS) {
                        upgradeable.addToTop(c);
                    }
                }

                if (upgradeable.size() > 0) {
                    upgradeable.shuffle();
                    upgradeable.group.get(0).upgrade();
                    upgradeable.group.get(0).superFlash();
                    upgradeable.group.get(0).applyPowers();

                    smithingHammerOrb.use(false);
                }

                if (AbstractDungeon.player.hasPower(WeaponPower.POWER_ID)) {
                    WeaponPower weaponPower = (WeaponPower) AbstractDungeon.player.getPower(WeaponPower.POWER_ID);
                    weaponPower.refreshWeapons();
                }

                this.isDone = true;
            }
        } else {
            this.tickDuration();
        }
    }

}
