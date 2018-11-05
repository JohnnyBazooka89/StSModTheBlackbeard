package blackbeard.patches;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

@SpirePatch(clz = FontHelper.class, method = "initialize", paramtypez = {})
public class FontHelperSingleCardViewPatch {

    public static final Logger logger = LogManager.getLogger(FontHelperSingleCardViewPatch.class);

    public static BitmapFont smallerSCPCardTitleFontSmall;

    @SpireInsertPatch(locator = Locator.class)
    public static void SCPCardTitleFontLocatorInsert() {
        try {
            Method prepFontMethod = FontHelper.class.getDeclaredMethod("prepFont", float.class, boolean.class);
            prepFontMethod.setAccessible(true);
            smallerSCPCardTitleFontSmall = (BitmapFont) prepFontMethod.invoke(null, 34.0f, true);
        } catch (Exception e) {
            logger.error("Exception in FontHelperSingleCardViewPatch: ", e);
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior method) throws Exception {
            Matcher matcher = new Matcher.FieldAccessMatcher(FontHelper.class, "SCP_cardTitleFont_small");
            return LineFinder.findInOrder(method, matcher);
        }
    }
}
