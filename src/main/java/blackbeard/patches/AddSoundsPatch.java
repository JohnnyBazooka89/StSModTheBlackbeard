package blackbeard.patches;

import basemod.ReflectionHacks;
import blackbeard.TheBlackbeardMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;

import java.util.HashMap;

@SpirePatch(clz = SoundMaster.class, method = SpirePatch.CONSTRUCTOR)
public class AddSoundsPatch {

    public static final String BLACKBEARD_YARR_SOUND_KEY = "BLACKBEARD_YARR";

    public static void Postfix(SoundMaster soundMaster) {
        HashMap<String, Sfx> map = (HashMap<String, Sfx>) ReflectionHacks.getPrivate(soundMaster, SoundMaster.class, "map");
        map.put(BLACKBEARD_YARR_SOUND_KEY, new Sfx(TheBlackbeardMod.getSoundFilePath("yarr"), false));
    }

}
