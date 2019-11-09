package blackbeard.patches;

import blackbeard.enums.PlayerClassEnum;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

@SpirePatch(clz = CharacterOption.class, method = "renderInfo", paramtypez = {SpriteBatch.class})
public class StarterRelicFixLongDescriptionPatch {

    public static SpireReturn Prefix(CharacterOption characterOption, SpriteBatch sb) {
        if (!characterOption.selected && characterOption.c.chosenClass == PlayerClassEnum.BLACKBEARD_CLASS) {
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }

}