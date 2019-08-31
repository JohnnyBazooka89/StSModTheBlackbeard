package blackbeard.patches;

import blackbeard.actions.EquipAction;
import blackbeard.characters.TheBlackbeard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;

@SpirePatch(clz = AbstractPlayer.class, method = "increaseMaxOrbSlots")
public class MaximumWeaponsReachedPatch {

    public static ExprEditor Instrument() {
        return new ExprEditor() {
            @Override
            public void edit(NewExpr newExpr) throws CannotCompileException {
                if (newExpr.getClassName().equals(ThoughtBubble.class.getName())) {
                    newExpr.replace(
                            "if(" + EquipAction.class.getName() + ".isInEquipActionWhileIncreasingMaxOrbSlots){" +
                                    "$_ = $proceed($1, $2, $3, " + TheBlackbeard.class.getName() + ".maximumWeaponsReachedStrings.TEXT[0], $5);" +
                                    "} else {" +
                                    "$_ = $proceed($$); " +
                                    "}"
                    );
                }
            }
        };
    }

}
