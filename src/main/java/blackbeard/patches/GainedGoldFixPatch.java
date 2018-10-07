package blackbeard.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

@SpirePatch(
        clz = CharSelectInfo.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = {
                String.class, String.class, int.class, int.class, int.class, int.class, int.class, AbstractPlayer.PlayerClass.class, ArrayList.class, ArrayList.class, boolean.class
        }
)


public class GainedGoldFixPatch {

    public static void Postfix(CharSelectInfo charSelectInfo, String name, String flavorText, int currentHp, int maxHp, int maxOrbs, int gold, int cardDraw, AbstractPlayer.PlayerClass color, ArrayList<String> relics, ArrayList<String> deck, boolean resumeGame) {
        CardCrawlGame.goldGained = gold;
    }

}
