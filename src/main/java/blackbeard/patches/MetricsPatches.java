package blackbeard.patches;

import basemod.ReflectionHacks;
import blackbeard.enums.PlayerClassEnum;
import com.badlogic.gdx.Net;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static blackbeard.TheBlackbeardMod.MOD_THE_SPIRE_MOD_ID;

/* Copied from The Mystic Mod:
   https://github.com/JohnnyDevo/The-Mystic-Project/blob/master/src/main/java/mysticmod/patches/MysticMetricsPatch.java
 */
public class MetricsPatches {

    private static final Logger logger = LogManager.getLogger(MetricsPatches.class);

    @SpirePatch(clz = Metrics.class, method = "sendPost", paramtypez = {String.class})
    public static class SendPostPatch {

        public static void Postfix(Metrics metrics, String fileName) {
            if (AbstractDungeon.player.chosenClass == PlayerClassEnum.BLACKBEARD_CLASS) {
                try {
                    Method sendPostMethod = ReflectionHacks.getCachedMethod(Metrics.class, "sendPost", String.class, String.class);
                    sendPostMethod.invoke(metrics, "http://www.theblackbeardmod.com/metrics/", fileName);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    logger.error("Could not send metrics for The Blackbeard", e);
                }
            }
        }

    }

    @SpirePatch(clz = Metrics.class, method = "sendPost", paramtypez = {String.class, String.class})
    public static class SendPutInsteadOfPostPatch {

        @SpireInsertPatch(locator = Locator.class, localvars = "httpRequest")
        public static void Insert(Metrics metrics, String url, String fileName, Net.HttpRequest httpRequest) {
            if (AbstractDungeon.player.chosenClass == PlayerClassEnum.BLACKBEARD_CLASS) {
                httpRequest.setMethod("PUT");
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior method) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(Net.HttpRequest.class, "setContent");
                return LineFinder.findInOrder(method, matcher);
            }
        }

    }


    @SpirePatch(clz = GameOverScreen.class, method = "shouldUploadMetricData")
    public static class ShouldUploadMetricData {

        public static boolean Postfix(boolean returnValue) {
            if (AbstractDungeon.player.chosenClass == PlayerClassEnum.BLACKBEARD_CLASS) {
                returnValue = Settings.UPLOAD_DATA;
            }
            return returnValue;
        }

    }

    @SpirePatch(clz = Metrics.class, method = "run")
    public static class RunPatch {

        public static void Postfix(Metrics metrics) {
            if (metrics.type == Metrics.MetricRequestType.UPLOAD_METRICS && AbstractDungeon.player.chosenClass == PlayerClassEnum.BLACKBEARD_CLASS) {
                try {
                    Method addDataMethod = Metrics.class.getDeclaredMethod("addData", Object.class, Object.class);
                    addDataMethod.setAccessible(true);
                    addDataMethod.invoke(metrics, "language", Settings.language);
                    addDataMethod.invoke(metrics, "blackbeardVersion", findTheBlackbeardVersion());

                    Method gatherAllDataAndSendMethod = Metrics.class.getDeclaredMethod("gatherAllDataAndSend", boolean.class, boolean.class, MonsterGroup.class);
                    gatherAllDataAndSendMethod.setAccessible(true);
                    gatherAllDataAndSendMethod.invoke(metrics, metrics.death, metrics.trueVictory, metrics.monsters);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    logger.error("Exception while sending metrics", e);
                }
            }
        }

        private static String findTheBlackbeardVersion() {
            return Arrays.stream(Loader.MODINFOS).filter(modInfo -> MOD_THE_SPIRE_MOD_ID.equals(modInfo.getIDName()))
                    .findFirst()
                    .map(modInfo -> modInfo.ModVersion.toString())
                    .orElse("Unknown");
        }

    }
}
