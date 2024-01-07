package blackbeard.variables;

import basemod.abstracts.DynamicVariable;
import blackbeard.interfaces.ISecondMagicNumber;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CustomHealMagicNumberVariable extends DynamicVariable {

    @Override
    public String key() {
        return "blackbeard:M2";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof ISecondMagicNumber) {
            return ((ISecondMagicNumber) card).isSecondMagicNumberModified();
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof ISecondMagicNumber) {
            return ((ISecondMagicNumber) card).secondMagicNumber();
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof ISecondMagicNumber) {
            return ((ISecondMagicNumber) card).baseSecondMagicNumber();
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof ISecondMagicNumber) {
            return ((ISecondMagicNumber) card).upgradedSecondMagicNumber();
        }
        return false;
    }

}