package blackbeard;

import basemod.*;
import basemod.abstracts.CustomRelic;
import basemod.eventUtil.AddEventParams;
import basemod.interfaces.*;
import blackbeard.cards.AbstractBlackbeardCard;
import blackbeard.characters.TheBlackbeard;
import blackbeard.enums.CardColorEnum;
import blackbeard.enums.PlayerClassEnum;
import blackbeard.events.BlackbeardSsssserpentEvent;
import blackbeard.events.BlackbeardVampiresEvent;
import blackbeard.events.GoldsmithEvent;
import blackbeard.events.ShipwreckEvent;
import blackbeard.potions.OrangeJuicePotion;
import blackbeard.potions.RumPotion;
import blackbeard.potions.UpgradePotion;
import blackbeard.utils.TextureLoader;
import blackbeard.variables.MagicNumberPlusOneVariable;
import blackbeard.variables.SecondMagicNumberVariable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.Skin;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.SlotData;
import com.esotericsoftware.spine.attachments.RegionAttachment;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

@SpireInitializer
public class TheBlackbeardMod implements PostInitializeSubscriber,
        EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber,
        EditStringsSubscriber, EditKeywordsSubscriber, AddAudioSubscriber, StartGameSubscriber {

    private static final Logger logger = LogManager.getLogger(TheBlackbeardMod.class);

    //Mod metadata
    private static final String MOD_NAME = "The Blackbeard";
    private static final String AUTHOR = "JohnnyBazooka89";
    private static final String DESCRIPTION = "Adds The Blackbeard as a new playable character.";
    public static final String MOD_THE_SPIRE_MOD_ID = "sts-mod-the-blackbeard";

    //General color
    private static final Color BLACK = CardHelper.getColor(0, 0, 0);

    //Card backgrounds and energy orbs
    private static final String ATTACK_BLACK = "blackbeard/img/512/bg_attack_black.png";
    private static final String SKILL_BLACK = "blackbeard/img/512/bg_skill_black.png";
    private static final String POWER_BLACK = "blackbeard/img/512/bg_power_black.png";
    private static final String ENERGY_ORB_BLACK = "blackbeard/img/512/card_black_orb.png";

    private static final String ATTACK_BLACK_PORTRAIT = "blackbeard/img/1024/bg_attack_black.png";
    private static final String SKILL_BLACK_PORTRAIT = "blackbeard/img/1024/bg_skill_black.png";
    private static final String POWER_BLACK_PORTRAIT = "blackbeard/img/1024/bg_power_black.png";
    private static final String ENERGY_ORB_BLACK_PORTRAIT = "blackbeard/img/1024/card_black_orb.png";

    private static final String ENERGY_ORB_IN_DESCRIPTION = "blackbeard/img/energy/energyOrbInDescription.png";

    //Assets
    private static final String BUTTON = "blackbeard/img/charSelect/blackbeardButton.png";
    private static final String PORTRAIT = "blackbeard/img/charSelect/blackbeardPortrait.jpg";

    //Badge
    private static final String BADGE_IMG = "blackbeard/img/ModBadge.png";

    //Localization strings
    private static final String CHARACTER_STRINGS_PATH = "blackbeard/localization/%s/CharacterStrings.json";
    private static final String RELIC_STRINGS_PATH = "blackbeard/localization/%s/RelicStrings.json";
    private static final String CARD_STRINGS_PATH = "blackbeard/localization/%s/CardStrings.json";
    private static final String POWER_STRINGS_PATH = "blackbeard/localization/%s/PowerStrings.json";
    private static final String ORB_STRINGS_PATH = "blackbeard/localization/%s/OrbStrings.json";
    private static final String KEYWORD_STRINGS_PATH = "blackbeard/localization/%s/KeywordStrings.json";
    private static final String POTION_STRINGS_PATH = "blackbeard/localization/%s/PotionStrings.json";
    private static final String EVENT_STRINGS_PATH = "blackbeard/localization/%s/EventStrings.json";
    private static final String UI_STRINGS_PATH = "blackbeard/localization/%s/UiStrings.json";

    //Languages
    private static final String DEFAULT_LANGUAGE_FOLDER = "eng";
    private static final String POLISH_LANGUAGE_FOLDER = "pol";
    private static final String RUSSIAN_LANGUAGE_FOLDER = "rus";
    private static final String SIMPLIFIED_CHINESE_LANGUAGE_FOLDER = "zhs";

    //Keywords
    public static Map<String, Keyword> blackbeardKeywords = new HashMap<>();
    public static final String WEAPON_KEYWORD = "blackbeard:WeaponKeyword";
    public static final String RESISTANCE_KEYWORD = "blackbeard:ResistanceKeyword";
    public static final String GOLDEN_CANNONBALL_KEYWORD = "blackbeard:GoldenCannonball";

    //Sounds
    public static final String SOUND_YARR_KEY = "BLACKBEARD_YARR";
    public static final String SOUND_YARR_FILEPATH = getSoundFilePath("yarr");

    //Christmas Theme
    public static final String BLACKBEARD_CHRISTMAS_HAT = "blackbeard/img/char/blackbeard/christmasHat.png";
    public static final String DISABLE_CHRISTMAS_THEME_KEY = "disableChristmasTheme";

    //Mod prefs
    public static final String BLACKBEARD_MOD_PREFS_ID = "BlackbeardModPrefs";
    public static final String BLACKBEARD_UI_SETTINGS_ID = "blackbeard:Settings";
    public static Prefs modPrefs; //initialized in receivePostInitialize

    //Custom Fonts
    public static BitmapFont fontForInfinitySymbol; //initialized in FontForInfinitySymbolPatch

    public TheBlackbeardMod() {
        BaseMod.subscribe(this);

        logger.info("Creating the color " + CardColorEnum.BLACKBEARD_BLACK.toString());

        BaseMod.addColor(CardColorEnum.BLACKBEARD_BLACK,
                BLACK,
                ATTACK_BLACK, SKILL_BLACK, POWER_BLACK,
                ENERGY_ORB_BLACK,
                ATTACK_BLACK_PORTRAIT, SKILL_BLACK_PORTRAIT, POWER_BLACK_PORTRAIT,
                ENERGY_ORB_BLACK_PORTRAIT, ENERGY_ORB_IN_DESCRIPTION);

    }

    public static String getCardImagePath(String cardId) {
        return "blackbeard/img/cards/" + cardId.replaceFirst("blackbeard:", "") + ".png";
    }

    public static String getRelicImagePath(String relicId) {
        return "blackbeard/img/relics/" + relicId.replaceFirst("blackbeard:", "") + ".png";
    }

    public static String getRelicOutlineImagePath(String relicId) {
        return "blackbeard/img/relics/outlines/" + relicId.replaceFirst("blackbeard:", "") + ".png";
    }

    public static String getOrbImagePath(String orbId) {
        return "blackbeard/img/orbs/" + orbId.replaceFirst("blackbeard:", "") + ".png";
    }

    public static String getPower128ImagePath(String powerId) {
        return "blackbeard/img/powers/128/" + powerId.replaceFirst("blackbeard:", "").replaceFirst("Power", "") + ".png";
    }

    public static String getPower48ImagePath(String powerId) {
        return "blackbeard/img/powers/48/" + powerId.replaceFirst("blackbeard:", "").replaceFirst("Power", "") + ".png";
    }

    public static String getVfxImagePath(String vfxName) {
        return "blackbeard/img/vfx/" + vfxName + ".png";
    }

    public static String getEventImagePath(String eventId) {
        return getJpgOrPngForPath("blackbeard/img/events/" + eventId.replaceFirst("blackbeard:", "").replaceFirst("Event", ""));
    }

    private static String getJpgOrPngForPath(String path) {
        if (Gdx.files.internal(path + ".jpg").exists()) {
            return path + ".jpg";
        }
        if (Gdx.files.internal(path + ".png").exists()) {
            return path + ".png";
        }
        throw new RuntimeException("Could not find neither jpg nor png for path: " + path);
    }

    public static String getSoundFilePath(String soundName) {
        return "blackbeard/sounds/" + soundName + ".wav";
    }

    public static String getHeartPanelImagePath(int i) {
        return "blackbeard/img/char/blackbeard/heart/blackbeard" + i + ".png";
    }

    public static void initialize() {
        logger.info("========================= THE BLACKBEARD INIT =========================");

        new TheBlackbeardMod();

        logger.info("=======================================================================");
    }

    @Override
    public void receivePostInitialize() {
        //Mod settings
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMG);
        ModPanel settingsPanel = new ModPanel();

        UIStrings uiSettingsStrings = CardCrawlGame.languagePack.getUIString(BLACKBEARD_UI_SETTINGS_ID);

        modPrefs = SaveHelper.getPrefs(BLACKBEARD_MOD_PREFS_ID);

        //Set default values to mod prefs
        if (!modPrefs.data.containsKey(DISABLE_CHRISTMAS_THEME_KEY)) {
            modPrefs.putBoolean(DISABLE_CHRISTMAS_THEME_KEY, false);
            modPrefs.flush();
        }

        settingsPanel.addUIElement(new ModLabeledToggleButton(uiSettingsStrings.TEXT[0], 360, 700, Settings.CREAM_COLOR, FontHelper.charDescFont, modPrefs.getBoolean(DISABLE_CHRISTMAS_THEME_KEY), settingsPanel, l -> {
        }, button -> {
            modPrefs.putBoolean(DISABLE_CHRISTMAS_THEME_KEY, button.enabled);
            modPrefs.flush();
        }));

        BaseMod.registerModBadge(badgeTexture, MOD_NAME, AUTHOR, DESCRIPTION, settingsPanel);

        //Events
        BaseMod.addEvent(BlackbeardSsssserpentEvent.ID, BlackbeardSsssserpentEvent.class, Exordium.ID);
        BaseMod.addEvent(BlackbeardVampiresEvent.ID, BlackbeardVampiresEvent.class, TheCity.ID);
        BaseMod.addEvent(new AddEventParams
                .Builder(GoldsmithEvent.EVENT_ID, GoldsmithEvent.class)
                .dungeonID(Exordium.ID)
                .bonusCondition(GoldsmithEvent.getCanBeEncounteredCondition())
                .create()
        );
        BaseMod.addEvent(new AddEventParams
                .Builder(ShipwreckEvent.EVENT_ID, ShipwreckEvent.class)
                .dungeonID(TheCity.ID)
                .bonusCondition(ShipwreckEvent.getCanBeEncounteredCondition())
                .create()
        );

        //Potions
        Color rumLiquidColor = new Color(211 / 255.0F, 102 / 255.0F, 0, 1);
        Color rumHybridColor = new Color(160 / 255.0F, 77 / 255.0F, 0, 1);
        Color orangeJuiceLiquidColor = new Color(255 / 255.0F, 179 / 255.0F, 63 / 255.0F, 1);
        Color orangeJuiceSpotsColor = new Color(255 / 255.0F, 154 / 255.0F, 0 / 255.0F, 1);
        BaseMod.addPotion(RumPotion.class, rumLiquidColor.cpy(), rumHybridColor.cpy(), null, "blackbeard:RumPotion", PlayerClassEnum.BLACKBEARD_CLASS);
        BaseMod.addPotion(OrangeJuicePotion.class, orangeJuiceLiquidColor.cpy(), null, orangeJuiceSpotsColor.cpy(), "blackbeard:OrangeJuicePotion", PlayerClassEnum.BLACKBEARD_CLASS);
        BaseMod.addPotion(UpgradePotion.class, Color.DARK_GRAY.cpy(), Color.CORAL.cpy(), null, "blackbeard:UpgradePotion", PlayerClassEnum.BLACKBEARD_CLASS);
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Begin editing characters");
        logger.info("Add " + PlayerClassEnum.BLACKBEARD_CLASS.toString());

        BaseMod.addCharacter(
                new TheBlackbeard(MOD_NAME),
                BUTTON,
                PORTRAIT,
                PlayerClassEnum.BLACKBEARD_CLASS
        );

        logger.info("Done editing characters");
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Begin editing relics");

        new AutoAdd(MOD_THE_SPIRE_MOD_ID)
                .packageFilter("blackbeard.relics")
                .any(CustomRelic.class, (info, relic) -> {
                    BaseMod.addRelicToCustomPool(relic, CardColorEnum.BLACKBEARD_BLACK);
                    UnlockTracker.markRelicAsSeen(relic.relicId);
                });

        logger.info("Done editing relics");
    }

    @Override
    public void receiveEditCards() {
        logger.info("Begin editing cards");
        logger.info("Add cards for " + PlayerClassEnum.BLACKBEARD_CLASS.toString());

        BaseMod.addDynamicVariable(new MagicNumberPlusOneVariable());
        BaseMod.addDynamicVariable(new SecondMagicNumberVariable());

        new AutoAdd(MOD_THE_SPIRE_MOD_ID)
                .packageFilter(AbstractBlackbeardCard.class)
                .setDefaultSeen(true)
                .cards();

        logger.info("Done editing cards");
    }

    @Override
    public void receiveEditStrings() {
        logger.info("Begin editing strings");

        loadCustomStringsForLanguage(DEFAULT_LANGUAGE_FOLDER);

        switch (Settings.language) {
            case POL:
                loadCustomStringsForLanguage(POLISH_LANGUAGE_FOLDER);
                break;
            case RUS:
                loadCustomStringsForLanguage(RUSSIAN_LANGUAGE_FOLDER);
                break;
            case ZHS:
                loadCustomStringsForLanguage(SIMPLIFIED_CHINESE_LANGUAGE_FOLDER);
                break;
            default:
                //Nothing - default language was already loaded
                break;
        }

        logger.info("Done editing strings");
    }

    private void loadCustomStringsForLanguage(String languageFolder) {
        BaseMod.loadCustomStringsFile(CharacterStrings.class, String.format(CHARACTER_STRINGS_PATH, languageFolder));
        BaseMod.loadCustomStringsFile(RelicStrings.class, String.format(RELIC_STRINGS_PATH, languageFolder));
        BaseMod.loadCustomStringsFile(CardStrings.class, String.format(CARD_STRINGS_PATH, languageFolder));
        BaseMod.loadCustomStringsFile(PowerStrings.class, String.format(POWER_STRINGS_PATH, languageFolder));
        BaseMod.loadCustomStringsFile(OrbStrings.class, String.format(ORB_STRINGS_PATH, languageFolder));
        BaseMod.loadCustomStringsFile(PotionStrings.class, String.format(POTION_STRINGS_PATH, languageFolder));
        BaseMod.loadCustomStringsFile(EventStrings.class, String.format(EVENT_STRINGS_PATH, languageFolder));
        BaseMod.loadCustomStringsFile(UIStrings.class, String.format(UI_STRINGS_PATH, languageFolder));
    }

    @Override
    public void receiveEditKeywords() {
        logger.info("Begin editing keywords");

        loadCustomKeywordsForLanguage(DEFAULT_LANGUAGE_FOLDER);

        switch (Settings.language) {
            case POL:
                loadCustomKeywordsForLanguage(POLISH_LANGUAGE_FOLDER);
                break;
            case RUS:
                loadCustomKeywordsForLanguage(RUSSIAN_LANGUAGE_FOLDER);
                break;
            case ZHS:
                loadCustomKeywordsForLanguage(SIMPLIFIED_CHINESE_LANGUAGE_FOLDER);
                break;
            default:
                //Nothing - default language was already loaded
                break;
        }

        logger.info("Done editing keywords");
    }

    private void loadCustomKeywordsForLanguage(String languageFolder) {
        Gson gson = new Gson();

        String keywordStrings = Gdx.files.internal(String.format(KEYWORD_STRINGS_PATH, languageFolder)).readString(String.valueOf(StandardCharsets.UTF_8));
        Type typeToken = new TypeToken<Map<String, Keyword>>() {
        }.getType();

        Map<String, Keyword> keywords = gson.fromJson(keywordStrings, typeToken);
        blackbeardKeywords.putAll(keywords);

        keywords.forEach((k, v) -> {
            if (v.PROPER_NAME != null) {
                BaseMod.addKeyword("blackbeard:", v.PROPER_NAME, v.NAMES, v.DESCRIPTION);
            } else {
                BaseMod.addKeyword(v.NAMES, v.DESCRIPTION);
            }
        });
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(SOUND_YARR_KEY, SOUND_YARR_FILEPATH);
    }

    public static boolean shouldUseChristmasTheme() {
        return !modPrefs.getBoolean(DISABLE_CHRISTMAS_THEME_KEY, false) && isChristmasTime();
    }

    private static boolean isChristmasTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate lastDay = now.with(TemporalAdjusters.firstDayOfNextYear()).toLocalDate();
        LocalDate firstDay = lastDay.minus(14, ChronoUnit.DAYS);
        return !now.isBefore(firstDay.atStartOfDay()) && !now.isAfter(lastDay.atStartOfDay());
    }

    @Override
    public void receiveStartGame() {
        if (AbstractDungeon.player.chosenClass == PlayerClassEnum.BLACKBEARD_CLASS && TheBlackbeardMod.shouldUseChristmasTheme()) {
            Skeleton skeleton = ReflectionHacks.getPrivate(AbstractDungeon.player, AbstractCreature.class, "skeleton");
            String attachName = "images/christmasHat";
            String attachCloneName = "images/christmasHatClone";
            int origSlotIndex = skeleton.findSlotIndex(attachName);

            // Create a new slot for the attachment
            Slot origSlot = skeleton.findSlot(attachName);
            Slot slotClone = new Slot(new SlotData(origSlot.getData().getIndex(), attachCloneName, origSlot.getBone().getData()), origSlot.getBone());
            slotClone.getData().setBlendMode(origSlot.getData().getBlendMode());
            skeleton.getSlots().insert(origSlotIndex, slotClone);

            // Add new slot to draw order
            Array<Slot> drawOrder = skeleton.getDrawOrder();
            drawOrder.add(slotClone);
            skeleton.setDrawOrder(drawOrder);

            Texture tex = ImageMaster.loadImage(BLACKBEARD_CHRISTMAS_HAT);
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            TextureRegion region = new TextureRegion(tex);
            RegionAttachment attachment = new RegionAttachment("images/christmasHatClone");
            attachment.setRegion(region);
            attachment.setWidth(tex.getWidth());
            attachment.setHeight(tex.getHeight());
            attachment.setX(-30.0f * Settings.scale);
            attachment.setY(60.0f * Settings.scale);
            attachment.setScaleX(0.5f * Settings.scale);
            attachment.setScaleY(0.5f * Settings.scale);
            attachment.setRotation(0.0f);
            attachment.updateOffset();

            Skin skin = skeleton.getData().getDefaultSkin();
            skin.addAttachment(origSlotIndex, attachment.getName(), attachment);

            skeleton.setAttachment(attachCloneName, attachment.getName());
        }
    }
}
