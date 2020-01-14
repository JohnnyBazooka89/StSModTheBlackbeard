package blackbeard.patches;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpirePatch(clz = FontHelper.class, method = "initialize", paramtypez = {})
public class FontHelperPrepareVerySmallDialogOptionFontPatch {

    public static BitmapFont verySmallDialogOptionFont;

    @SpireInsertPatch(locator = Locator.class)
    public static void Insert() {
        try {
            Method m = FontHelper.class.getDeclaredMethod("prepFont", float.class, boolean.class);
            m.setAccessible(true);
            verySmallDialogOptionFont = (BitmapFont) m.invoke(null, 23.0F, false);
            verySmallDialogOptionFont.getData().markupEnabled = false;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            verySmallDialogOptionFont = FontHelper.smallDialogOptionFont;
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.FieldAccessMatcher(FontHelper.class, "smallDialogOptionFont");
            return new int[]{LineFinder.findInOrder(ctMethodToPatch, matcher)[0] + 2};
        }
    }

}
