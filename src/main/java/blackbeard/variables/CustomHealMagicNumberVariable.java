package blackbeard.variables;

import basemod.abstracts.DynamicVariable;
import blackbeard.interfaces.ICustomHealMagicNumber;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CustomHealMagicNumberVariable extends DynamicVariable {

    @Override
    public String key() {
        return "blackbeard:CustomHeal";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof ICustomHealMagicNumber) {
            return ((ICustomHealMagicNumber) card).isModified();
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof ICustomHealMagicNumber) {
            return ((ICustomHealMagicNumber) card).value();
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof ICustomHealMagicNumber) {
            return ((ICustomHealMagicNumber) card).baseValue();
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof ICustomHealMagicNumber) {
            return ((ICustomHealMagicNumber) card).upgraded();
        }
        return false;
    }

}