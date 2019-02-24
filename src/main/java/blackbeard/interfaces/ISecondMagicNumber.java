package blackbeard.interfaces;


import com.megacrit.cardcrawl.cards.AbstractCard;

public interface ISecondMagicNumber {

    boolean isModified(AbstractCard card);

    int value(AbstractCard card);

    int baseValue(AbstractCard card);

    boolean upgraded(AbstractCard card);

}
