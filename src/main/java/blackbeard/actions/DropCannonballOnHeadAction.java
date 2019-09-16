package blackbeard.actions;

import blackbeard.effects.DropCannonballOnHeadEffect;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DropCannonballOnHeadAction extends AbstractGameAction {

    private DropCannonballOnHeadEffect effect;
    private Texture texture;

    public DropCannonballOnHeadAction(AbstractMonster target, Texture texture) {
        this.actionType = ActionType.SPECIAL;
        this.target = target;
        this.texture = texture;
    }


    public void update() {
        if (effect == null) {
            effect = new DropCannonballOnHeadEffect((AbstractMonster) target, texture, 15);
            AbstractDungeon.effectList.add(effect);
        } else if (effect.finishedAction) {
            this.isDone = true;
        }
    }
}
