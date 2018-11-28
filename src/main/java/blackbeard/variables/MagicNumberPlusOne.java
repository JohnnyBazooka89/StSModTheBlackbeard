package blackbeard.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class MagicNumberPlusOne extends DynamicVariable {
    @Override
    public String key() {
        return "blackbeard:M+1";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return card.isMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card) {
        return card.magicNumber + 1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card.baseMagicNumber + 1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card.upgradedMagicNumber;
    }

}