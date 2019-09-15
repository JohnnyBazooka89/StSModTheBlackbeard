package blackbeard.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class UnconditionalWaitAction extends AbstractGameAction {

    public UnconditionalWaitAction(float duration) {
        this.duration = duration;
    }

    @Override
    public void update() {
        this.tickDuration();
    }

}
