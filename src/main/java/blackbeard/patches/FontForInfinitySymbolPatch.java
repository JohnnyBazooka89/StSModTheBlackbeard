package blackbeard.patches;

import basemod.ReflectionHacks;
import blackbeard.TheBlackbeardMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CtBehavior;

import java.lang.reflect.Method;

@SpirePatch(clz = FontHelper.class, method = "initialize", paramtypez = {})
public class FontForInfinitySymbolPatch {

    @SpireInsertPatch(locator = Locator.class)
    public static void Insert() throws Exception {
        FileHandle originalFontFile = (FileHandle) ReflectionHacks.getPrivateStatic(FontHelper.class, "fontFile");

        ReflectionHacks.setPrivateStatic(FontHelper.class, "fontFile", Gdx.files.internal("font/zht/NotoSansCJKtc-Regular.otf"));

        Method prepFontMethod = FontHelper.class.getDeclaredMethod("prepFont", float.class, boolean.class);
        prepFontMethod.setAccessible(true);
        TheBlackbeardMod.fontForInfinitySymbol = (BitmapFont) prepFontMethod.invoke(null, 38.0F, true);

        ReflectionHacks.setPrivateStatic(FontHelper.class, "fontFile", originalFontFile);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior method) throws Exception {
            Matcher matcher = new Matcher.FieldAccessMatcher(FontHelper.class, "cardEnergyFont_L");
            return LineFinder.findInOrder(method, matcher);
        }
    }
}
