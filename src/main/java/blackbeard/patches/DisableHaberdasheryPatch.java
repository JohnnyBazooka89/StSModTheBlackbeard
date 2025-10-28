package blackbeard.patches;

import blackbeard.enums.PlayerClassEnum;
import com.esotericsoftware.spine.Skeleton;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(
        cls = "com.evacipated.cardcrawl.mod.haberdashery.AttachRelic",
        method = "updateSlotVisibilities$haberdashery",
        requiredModId = "haberdashery"
)
public class DisableHaberdasheryPatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(Object attachRelic, AbstractPlayer player, Skeleton skeleton) {
        if (player.chosenClass == PlayerClassEnum.BLACKBEARD_CLASS) {
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}