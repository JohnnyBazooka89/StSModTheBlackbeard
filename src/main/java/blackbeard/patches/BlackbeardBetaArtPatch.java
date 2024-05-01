package blackbeard.patches;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.enums.CardColorEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

@SpirePatch(clz = AbstractCard.class, method = "renderImage")
@SpirePatch(clz = CustomCard.class, method = "getPortraitImage", paramtypez = {})
public class BlackbeardBetaArtPatch {
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            @Override
            public void edit(FieldAccess fieldAccess) throws CannotCompileException {
                if (fieldAccess.getFieldName().equals("PLAYTESTER_ART_MODE")) {
                    fieldAccess.replace("$_ = ($proceed() || " + BlackbeardBetaArtPatch.class.getName() + ".shouldUseBetaArtForTheBlackbeardCard(this));");
                }
            }
        };
    }

    public static boolean shouldUseBetaArtForTheBlackbeardCard(AbstractCard card) {
        return card.color == CardColorEnum.BLACKBEARD_BLACK && TheBlackbeardMod.modPrefs.getBoolean(TheBlackbeardMod.USE_BETA_ART_FOR_THE_BLACKBEARD_CARDS_KEY, false);
    }
}
