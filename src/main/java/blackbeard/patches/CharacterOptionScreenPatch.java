package blackbeard.patches;

import blackbeard.relics.LoadTheCannons;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(clz = CharacterOption.class, method = "renderRelics")
public class CharacterOptionScreenPatch {

    public static ExprEditor Instrument() {
        final int[] counter = {0};
        return new ExprEditor() {
            @Override
            public void edit(MethodCall methodCall) throws CannotCompileException {
                if (methodCall.getMethodName().equals("renderSmartText")) {
                    if (counter[0] == 1) {
                        methodCall.replace(
                                "if(" + LoadTheCannons.class.getName() + ".ID" + ".equals(" + RelicLibrary.class.getName() + ".getRelic((String) this.charInfo.relics.get(0)).relicId)) { " +
                                        "$proceed($1, $2, (" + RelicLibrary.class.getName() + ".getRelic((String) this.charInfo.relics.get(0)).DESCRIPTIONS[1]" + "), $4, $5, $6, (30.0F * " + Settings.class.getName() + ".scale), $8);" +
                                        "} else {" +
                                        "$proceed($$); " +
                                        "}"
                        );
                    }
                    counter[0]++;
                }
            }
        };
    }

}
