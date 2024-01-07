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
            return ((ICustomHealMagicNumber) card).isCustomHealMagicNumberModified();
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof ICustomHealMagicNumber) {
            return ((ICustomHealMagicNumber) card).customHealMagicNumberValue();
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof ICustomHealMagicNumber) {
            return ((ICustomHealMagicNumber) card).customHealMagicNumberBaseValue();
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof ICustomHealMagicNumber) {
            return ((ICustomHealMagicNumber) card).customHealMagicNumberUpgraded();
        }
        return false;
    }

}