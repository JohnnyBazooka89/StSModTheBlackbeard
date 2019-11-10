package blackbeard.patches;

import blackbeard.enums.CardTagsEnum;
import blackbeard.relics.CannonballsOfSteel;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;

public class CannonballsOfSteelPatches {

    @SpirePatch(clz = AbstractCard.class, method = "applyPowers")
    public static class CannonballsOfSteelApplyPowersPatch {

        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
        public static void Insert(AbstractCard abstractCard, @ByRef float[] tmp) {
            if (AbstractDungeon.player.hasRelic(CannonballsOfSteel.ID) && abstractCard.hasTag(CardTagsEnum.CANNONBALL)) {
                tmp[0] += 2.0F;
                if (abstractCard.baseDamage != (int) tmp[0]) {
                    abstractCard.isDamageModified = true;
                }
            }
        }

        private static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior method) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return new int[]{LineFinder.findInOrder(method, matcher)[0]};
            }
        }

    }

    @SpirePatch(clz = AbstractCard.class, method = "calculateCardDamage")
    public static class CannonballsOfSteelCalculateCardDamagePatch {

        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
        public static void Insert(AbstractCard abstractCard, AbstractMonster mo, @ByRef float[] tmp) {
            if (AbstractDungeon.player.hasRelic(CannonballsOfSteel.ID) && abstractCard.hasTag(CardTagsEnum.CANNONBALL)) {
                tmp[0] += 2.0F;
                if (abstractCard.baseDamage != (int) tmp[0]) {
                    abstractCard.isDamageModified = true;
                }
            }
        }

        private static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior method) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return new int[]{LineFinder.findInOrder(method, matcher)[0]};
            }
        }

    }

}
