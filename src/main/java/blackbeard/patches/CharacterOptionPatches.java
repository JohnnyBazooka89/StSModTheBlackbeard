package blackbeard.patches;

import blackbeard.enums.PlayerClassEnum;
import blackbeard.relics.LoadTheCannons;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class CharacterOptionPatches {

    @SpirePatch(clz = CharacterOption.class, method = "renderRelics")
    public static class StarterRelicDescriptionPatch {

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

    @SpirePatch(clz = CharacterOption.class, method = "renderInfo")
    public static class UnlocksPatch {

        public static ExprEditor Instrument() {
            final int[] counter = {0};
            return new ExprEditor() {
                @Override
                public void edit(MethodCall methodCall) throws CannotCompileException {
                    if (methodCall.getMethodName().equals("renderSmartText")) {
                        if (counter[0] == 4 || counter[0] == 5) {
                            methodCall.replace(
                                    "if(this.c.chosenClass == " + PlayerClassEnum.class.getName() + ".BLACKBEARD_CLASS" + ") { " +
                                            "$proceed($1, $2, $3, $4, ($5 - (30.0F * " + Settings.class.getName() + ".scale)), $6, $7, $8);" +
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

}