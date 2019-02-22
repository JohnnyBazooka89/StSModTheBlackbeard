package blackbeard.patches;

import blackbeard.enums.TheBlackbeardEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.events.exordium.Sssserpent;

public class BlackbeardExclusiveEventsPatches {

    @SpirePatch(clz = Exordium.class, method = "initializeEventList")
    public static class initializeEventListExordium {
        public static void Postfix(AbstractDungeon dungeon) {
            if (dungeon.player.chosenClass == TheBlackbeardEnum.BLACKBEARD_CLASS) {
                dungeon.eventList.removeIf(d -> d.equals(Sssserpent.ID));
            }
        }
    }

}