package blackbeard.patches;

import basemod.ReflectionHacks;
import blackbeard.interfaces.ILongTitle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

@SpirePatch(clz = SingleCardViewPopup.class, method = "renderTitle", paramtypez = {SpriteBatch.class})
public class LongTitlesSingleCardViewFixPatch {

    private static BitmapFont originalSCPCardTitleFontSmall;

    public static void Prefix(SingleCardViewPopup singleCardViewPopup, SpriteBatch sb) {
        originalSCPCardTitleFontSmall = FontHelper.SCP_cardTitleFont_small;

        AbstractCard abstractCard = (AbstractCard) ReflectionHacks.getPrivate(singleCardViewPopup, SingleCardViewPopup.class, "card");
        if (shouldFixLongTitle(abstractCard)) {
            FontHelper.SCP_cardTitleFont_small = FontHelperSingleCardViewPatch.smallerSCPCardTitleFontSmall;
        }
    }

    public static void Postfix(SingleCardViewPopup singleCardViewPopup, SpriteBatch sb) {
        FontHelper.SCP_cardTitleFont_small = originalSCPCardTitleFontSmall;
    }

    private static boolean shouldFixLongTitle(AbstractCard abstractCard) {
        return abstractCard instanceof ILongTitle;
    }

}
