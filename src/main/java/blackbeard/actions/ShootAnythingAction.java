package blackbeard.actions;

import blackbeard.effects.ShootAnythingEffect;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShootAnythingAction extends AbstractGameAction {

    private ShootAnythingEffect effect;

    public ShootAnythingAction(AbstractMonster target, Texture texture) {
        this.actionType = ActionType.SPECIAL;

        effect = new ShootAnythingEffect(target, texture, 15);
        AbstractDungeon.effectList.add(effect);
    }


    public void update() {
        if (effect.finishedAction) {
            this.isDone = true;
        }
    }
}
