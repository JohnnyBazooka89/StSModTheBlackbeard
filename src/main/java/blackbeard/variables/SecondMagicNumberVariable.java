package blackbeard.variables;

import basemod.abstracts.DynamicVariable;
import blackbeard.cards.interfaces.SecondMagicNumber;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static blackbeard.TheBlackbeardMod.makeID;

public class SecondMagicNumberVariable extends DynamicVariable {

    @Override
    public String key() {
        return makeID("M2");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof SecondMagicNumber) {
            return ((SecondMagicNumber) card).isSecondMagicNumberModified();
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof SecondMagicNumber) {
            return ((SecondMagicNumber) card).secondMagicNumber();
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof SecondMagicNumber) {
            return ((SecondMagicNumber) card).baseSecondMagicNumber();
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof SecondMagicNumber) {
            return ((SecondMagicNumber) card).upgradedSecondMagicNumber();
        }
        return false;
    }

}