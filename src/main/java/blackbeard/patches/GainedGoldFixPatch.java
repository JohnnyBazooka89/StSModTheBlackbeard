package blackbeard.patches;

import blackbeard.characters.TheBlackbeard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

@SpirePatch(clz = CharSelectInfo.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {String.class, String.class, int.class, int.class, int.class, int.class, int.class, AbstractPlayer.class, ArrayList.class, ArrayList.class, boolean.class})
public class GainedGoldFixPatch {

    public static void Postfix(CharSelectInfo charSelectInfo, String name, String flavorText, int currentHp, int maxHp, int maxOrbs, int gold, int cardDraw, AbstractPlayer player, ArrayList<String> relics, ArrayList<String> deck, boolean resumeGame) {
        if (player instanceof TheBlackbeard) {
            CardCrawlGame.goldGained = gold;
        }
    }

}