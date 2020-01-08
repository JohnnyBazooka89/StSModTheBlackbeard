package blackbeard.patches;

import blackbeard.events.GoldsmithEvent;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(clz = LargeDialogOptionButton.class, method = "render", paramtypez = {SpriteBatch.class})
public class UseVerySmallDialogOptionFontInGoldsmithEventPatch {

    public static ExprEditor Instrument() {
        return new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getMethodName().equals("renderSmartText")) {
                    m.replace(
                            "$_ = $proceed($1, (" + UseVerySmallDialogOptionFontInGoldsmithEventPatch.class.getName() + ".shouldChangeFont($3) ? " + FontHelperPrepareVerySmallDialogOptionFontPatch.class.getName() + ".verySmallDialogOptionFont : $2), $3, $4, $5, $6, $7, $8);"
                    );
                }
            }
        };
    }

    public static boolean shouldChangeFont(String msg) {
        return msg != null && (msg.equals(GoldsmithEvent.getStrikeOptionText()) || msg.equals(GoldsmithEvent.getDefendOptionText()));
    }

}
