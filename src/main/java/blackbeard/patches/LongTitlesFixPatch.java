package blackbeard.patches;

import blackbeard.interfaces.ILongTitle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.FontHelper;

@SpirePatch(clz = AbstractCard.class, method = "renderTitle", paramtypez = {SpriteBatch.class})
public class LongTitlesFixPatch {

    private static BitmapFont originalCardTitleFontSmallN;
    private static BitmapFont originalCardTitleFontSmallL;

    public static void Prefix(AbstractCard abstractCard, SpriteBatch sb) {
        originalCardTitleFontSmallN = FontHelper.cardTitleFont_small_N;
        originalCardTitleFontSmallL = FontHelper.cardTitleFont_small_L;

        if (shouldFixLongTitle(abstractCard)) {
            FontHelper.cardTitleFont_small_N = FontHelperPatch.smallerCardTitleFontSmallN;
            FontHelper.cardTitleFont_small_L = FontHelperPatch.smallerCardTitleFontSmallL;
        }
    }

    public static void Postfix(AbstractCard abstractCard, SpriteBatch sb) {
        FontHelper.cardTitleFont_small_N = originalCardTitleFontSmallN;
        FontHelper.cardTitleFont_small_L = originalCardTitleFontSmallL;
    }

    private static boolean shouldFixLongTitle(AbstractCard abstractCard) {
        return abstractCard instanceof ILongTitle;
    }

}
