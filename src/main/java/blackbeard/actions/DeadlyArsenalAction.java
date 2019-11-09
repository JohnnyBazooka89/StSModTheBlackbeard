package blackbeard.actions;

import blackbeard.orbs.AbstractWeaponOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;

public class DeadlyArsenalAction extends AbstractGameAction {

    public DeadlyArsenalAction(AbstractPlayer source) {
        this.source = source;
        this.actionType = ActionType.WAIT;
    }

    public void update() {

        ArrayList<String> weaponList = new ArrayList();

        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof AbstractWeaponOrb) {
                if (!weaponList.contains(orb.ID)) {
                    weaponList.add(orb.ID);
                }
            }
        }

        int toDraw = weaponList.size();
        if (toDraw > 0) {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.source, toDraw));
        }

        this.isDone = true;
    }
}
