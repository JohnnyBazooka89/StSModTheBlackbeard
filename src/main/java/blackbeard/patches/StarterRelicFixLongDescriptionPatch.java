package blackbeard.patches;

import blackbeard.enums.PlayerClassEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

@SpirePatch(clz = CharacterOption.class, method = "renderInfo")
public class StarterRelicFixLongDescriptionPatch {

    public static SpireReturn Prefix(CharacterOption characterOption) {
        if (!characterOption.selected && characterOption.c.chosenClass == PlayerClassEnum.BLACKBEARD_CLASS) {
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }

}