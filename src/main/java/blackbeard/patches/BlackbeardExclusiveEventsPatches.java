package blackbeard.patches;

import blackbeard.enums.TheBlackbeardEnum;
import blackbeard.events.SsssserpentBlackbeardEvent;
import blackbeard.events.VampiresBlackbeardEvent;
import javassist.CtBehavior;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.events.exordium.Sssserpent;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;

@SpirePatch(clz = AbstractDungeon.class, method = "getEvent")
public class BlackbeardExclusiveEventsPatches {

    @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
    public static void Insert(Random rng, ArrayList<String> tmp) {
        if (AbstractDungeon.player.chosenClass == TheBlackbeardEnum.BLACKBEARD_CLASS) {
            tmp.removeIf(d -> d.equals(Sssserpent.ID));
            tmp.removeIf(d -> d.equals(Vampires.ID));
        } else {
            tmp.removeIf(d -> d.equals(SsssserpentBlackbeardEvent.ID));
            tmp.removeIf(d -> d.equals(VampiresBlackbeardEvent.ID));
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ArrayList.class, "isEmpty");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }

}
