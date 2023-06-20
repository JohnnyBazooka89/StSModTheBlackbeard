package blackbeard.patches;

import basemod.ReflectionHacks;
import blackbeard.powers.DrunkenSailorPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import javassist.CtBehavior;

@SpirePatch(clz = PotionPopUp.class, method = "updateInput")
@SpirePatch(clz = PotionPopUp.class, method = "updateTargetMode")
public class DrunkenSailorPatch {

    @SpireInsertPatch(locator = Locator.class, localvars = {"potion"})
    public static void Insert(PotionPopUp potionPopUp, AbstractPotion potion) {
        int amountOfAdditionalUsesOfPotion = 0;

        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof DrunkenSailorPower) {
                DrunkenSailorPower drunkenSailorPower = (DrunkenSailorPower) power;
                amountOfAdditionalUsesOfPotion += drunkenSailorPower.amount;
            }
        }
        for (int i = 0; i < amountOfAdditionalUsesOfPotion; i++) {
            AbstractMonster hoveredMonster = (AbstractMonster) ReflectionHacks.getPrivate(potionPopUp, PotionPopUp.class, "hoveredMonster");
            potion.use(hoveredMonster);
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior method) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractPotion.class, "use");
            return LineFinder.findInOrder(method, matcher);
        }
    }

}
