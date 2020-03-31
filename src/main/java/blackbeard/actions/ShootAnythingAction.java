package blackbeard.actions;

import blackbeard.effects.ShootAnythingEffect;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShootAnythingAction extends AbstractGameAction {

    private ShootAnythingEffect effect;
    private Texture texture;
    private boolean dropOnHead;

    public ShootAnythingAction(AbstractCreature target, Texture texture, boolean dropOnHead) {
        this.actionType = ActionType.SPECIAL;
        this.target = target;
        this.texture = texture;
        this.dropOnHead = dropOnHead;
    }


    public void update() {
        if (effect == null) {
            this.effect = new ShootAnythingEffect(target, texture, dropOnHead);
            AbstractDungeon.effectList.add(effect);
        } else if (effect.isDone) {
            this.isDone = true;
        }
    }
}
