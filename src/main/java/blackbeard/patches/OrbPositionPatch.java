package blackbeard.patches;

import blackbeard.TheBlackbeardMod;
import blackbeard.enums.PlayerClassEnum;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SpirePatch(clz = AbstractOrb.class, method = "setSlot", paramtypez = {int.class, int.class})
public class OrbPositionPatch {

    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(AbstractOrb abstractOrb, int slotNum, int maxOrbs) {
        if (AbstractDungeon.player.chosenClass == PlayerClassEnum.BLACKBEARD_CLASS && TheBlackbeardMod.shouldUseChristmasTheme() && maxOrbs == 1) {
            abstractOrb.tY += 30.0F * Settings.scale;
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.MethodCallMatcher(Hitbox.class, "move");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }

}
