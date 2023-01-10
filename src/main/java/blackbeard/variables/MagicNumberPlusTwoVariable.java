package blackbeard.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class MagicNumberPlusTwoVariable extends DynamicVariable {
    @Override
    public String key() {
        return "blackbeard:M+2";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return card.isMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card) {
        return card.magicNumber + 2;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card.baseMagicNumber + 2;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card.upgradedMagicNumber;
    }

}