package blackbeard.patches;

import basemod.ReflectionHacks;
import blackbeard.powers.TheDrunkenSailorPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatches(
        value = {
                @SpirePatch(clz = AncientPotion.class, method = "use"),
                @SpirePatch(clz = AttackPotion.class, method = "use"),
                @SpirePatch(clz = BlockPotion.class, method = "use"),
                @SpirePatch(clz = BloodPotion.class, method = "use"),
                @SpirePatch(clz = DexterityPotion.class, method = "use"),
                @SpirePatch(clz = EnergyPotion.class, method = "use"),
                @SpirePatch(clz = EntropicBrew.class, method = "use"),
                @SpirePatch(clz = EssenceOfSteel.class, method = "use"),
                @SpirePatch(clz = ExplosivePotion.class, method = "use"),
                @SpirePatch(clz = FairyPotion.class, method = "use"),
                @SpirePatch(clz = FearPotion.class, method = "use"),
                @SpirePatch(clz = FirePotion.class, method = "use"),
                @SpirePatch(clz = FocusPotion.class, method = "use"),
                @SpirePatch(clz = FruitJuice.class, method = "use"),
                @SpirePatch(clz = GamblersBrew.class, method = "use"),
                @SpirePatch(clz = GhostInAJar.class, method = "use"),
                @SpirePatch(clz = LiquidBronze.class, method = "use"),
                @SpirePatch(clz = PoisonPotion.class, method = "use"),
                @SpirePatch(clz = PotionSlot.class, method = "use"),
                @SpirePatch(clz = PowerPotion.class, method = "use"),
                @SpirePatch(clz = RegenPotion.class, method = "use"),
                @SpirePatch(clz = SkillPotion.class, method = "use"),
                @SpirePatch(clz = SmokeBomb.class, method = "use"),
                @SpirePatch(clz = SneckoOil.class, method = "use"),
                @SpirePatch(clz = SpeedPotion.class, method = "use"),
                @SpirePatch(clz = SteroidPotion.class, method = "use"),
                @SpirePatch(clz = StrengthPotion.class, method = "use"),
                @SpirePatch(clz = SwiftPotion.class, method = "use"),
                @SpirePatch(clz = WeakenPotion.class, method = "use")
        }
)

public class TheDrunkenSailorPatch {

    public static void Prefix(AbstractPotion potion, AbstractCreature target) {

        int potencyMultiplier = 1;

        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof TheDrunkenSailorPower) {
                TheDrunkenSailorPower theDrunkenSailorPower = (TheDrunkenSailorPower) power;
                potencyMultiplier += theDrunkenSailorPower.amount;
            }
        }

        ReflectionHacks.setPrivate(potion, AbstractPotion.class, "potency", potencyMultiplier * potion.getPotency());
    }

    public static void Postfix(AbstractPotion potion, AbstractCreature target) {
        ReflectionHacks.setPrivate(potion, AbstractPotion.class, "potency", potion.getPotency());
    }

}
