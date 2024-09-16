package blackbeard.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.beyond.SensoryStone;
import com.megacrit.cardcrawl.localization.EventStrings;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.Collections;

@SpirePatch(clz = SensoryStone.class, method = "getRandomMemory")
public class SensoryStonePatch {
    public static final String EVENT_ID = "SensoryStone";
    public static EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    public static String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;

    @SpireInsertPatch(locator = Locator.class, localvars = {"memories"})
    public static void Insert(SensoryStone sensoryStone, ArrayList<String> memories) {
        memories.add(DESCRIPTIONS[0]);
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.MethodCallMatcher(Collections.class, "shuffle");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}