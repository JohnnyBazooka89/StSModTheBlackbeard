package blackbeard.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class WeaponDamageAllEnemiesAction extends AbstractGameAction {
    private int damage;

    public WeaponDamageAllEnemiesAction(int damage) {
        this.damage = damage;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction((AbstractCreature) null, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        this.isDone = true;
    }
}
