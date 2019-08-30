package blackbeard.actions;

import blackbeard.utils.ShootAnythingEffect;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShootAnythingAction extends AbstractGameAction {

    private ShootAnythingEffect rrfe;

    public ShootAnythingAction(AbstractMonster target, Texture texture) {
        this.actionType = ActionType.SPECIAL;

        rrfe = new ShootAnythingEffect(target, texture, 15);
        AbstractDungeon.effectList.add(rrfe);
    }


    public void update() {
        if (rrfe.finishedAction)
            this.isDone = true;
    }
}
