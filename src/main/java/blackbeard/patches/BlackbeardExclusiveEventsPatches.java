package blackbeard.patches;

import blackbeard.enums.TheBlackbeardEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.events.exordium.Sssserpent;

public class BlackbeardExclusiveEventsPatches {

    @SpirePatch(clz = Exordium.class, method = "initializeEventList")
    public static class InitializeEventListExordium {
        public static void Postfix(AbstractDungeon dungeon) {
            if (AbstractDungeon.player.chosenClass == TheBlackbeardEnum.BLACKBEARD_CLASS) {
                AbstractDungeon.eventList.removeIf(d -> d.equals(Sssserpent.ID));
            }
        }
    }

}