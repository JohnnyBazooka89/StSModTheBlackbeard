package blackbeard.patches;

import blackbeard.enums.PlayerClassEnum;
import blackbeard.events.BlackbeardSsssserpentEvent;
import blackbeard.events.BlackbeardVampiresEvent;
import blackbeard.events.GoldsmithEvent;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.events.exordium.Sssserpent;
import com.megacrit.cardcrawl.random.Random;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(clz = AbstractDungeon.class, method = "getEvent")
public class BlackbeardExclusiveEventsPatch {

    @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
    public static void Insert(Random rng, ArrayList<String> tmp) {
        if (AbstractDungeon.player.chosenClass == PlayerClassEnum.BLACKBEARD_CLASS) {
            tmp.removeIf(d -> d.equals(Sssserpent.ID));
            tmp.removeIf(d -> d.equals(Vampires.ID));
        } else {
            tmp.removeIf(d -> d.equals(BlackbeardSsssserpentEvent.ID));
            tmp.removeIf(d -> d.equals(BlackbeardVampiresEvent.ID));
            tmp.removeIf(d -> d.equals(GoldsmithEvent.EVENT_ID));
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
