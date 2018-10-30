package blackbeard.actions;

import blackbeard.orbs.DaggerOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class DaggerAccumulationAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int attack;
    private int durability;
    private AbstractPlayer p;
    private int energyOnUse;

    public DaggerAccumulationAction(AbstractPlayer p, int attack, int durability, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.attack = attack;
        this.durability = durability;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {
            for (int i = 0; i < effect; ++i) {
                AbstractDungeon.actionManager.addToBottom(new EquipAction(new DaggerOrb(attack, durability, false)));
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
