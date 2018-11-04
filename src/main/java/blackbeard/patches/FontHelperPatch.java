package blackbeard.patches;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

@SpirePatch(clz = FontHelper.class, method = "initialize", paramtypez = {})
public class FontHelperPatch {

    public static final Logger logger = LogManager.getLogger(FontHelperPatch.class);

    public static BitmapFont smallerCardTitleFontSmallN;
    public static BitmapFont smallerCardTitleFontSmallL;

    @SpireInsertPatch(locator = Locator.class)
    public static void CardTitleFontInsert() {
        try {
            Method prepFontMethod = FontHelper.class.getDeclaredMethod("prepFont", float.class, boolean.class);
            prepFontMethod.setAccessible(true);
            smallerCardTitleFontSmallN = (BitmapFont) prepFontMethod.invoke(null, 17.0f, true);
            smallerCardTitleFontSmallL = (BitmapFont) prepFontMethod.invoke(null, 17.0f, true);
        } catch (Exception e) {
            logger.error("Exception in FontHelperPatch: ", e);
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior method) throws Exception {
            Matcher matcher = new Matcher.FieldAccessMatcher(FontHelper.class, "cardTitleFont_small_N");
            return LineFinder.findInOrder(method, matcher);
        }
    }

}
