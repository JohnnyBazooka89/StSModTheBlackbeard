package blackbeard;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import blackbeard.cards.*;
import blackbeard.characters.TheBlackbeard;
import blackbeard.enums.CardColorEnum;
import blackbeard.enums.PlayerClassEnum;
import blackbeard.events.SsssserpentBlackbeardEvent;
import blackbeard.events.VampiresBlackbeardEvent;
import blackbeard.potions.RumPotion;
import blackbeard.potions.ToastPotion;
import blackbeard.relics.*;
import blackbeard.variables.MagicNumberPlusOneVariable;
import blackbeard.variables.SecondMagicNumberVariable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.potions.BloodPotion;
import com.megacrit.cardcrawl.potions.GhostInAJar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpireInitializer
public class TheBlackbeardMod implements PostInitializeSubscriber,
        EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber,
        EditStringsSubscriber, EditKeywordsSubscriber, AddAudioSubscriber {

    private static final Logger logger = LogManager.getLogger(TheBlackbeardMod.class);

    //Default cards and orbs
    public static final String DEFAULT_POWER_CARD_ID = "blackbeard:BetaPower";
    public static final String DEFAULT_SKILL_CARD_ID = "blackbeard:BetaSkill";
    public static final String DEFAULT_ATTACK_CARD_ID = "blackbeard:Beta";

    //Mod metadata
    private static final String MOD_NAME = "The Blackbeard";
    private static final String AUTHOR = "JohnnyBazooka89";
    private static final String DESCRIPTION = "Adds The Blackbeard as a new playable character.";

    //General color
    private static final Color BLACK = CardHelper.getColor(0.0f, 0.0f, 0.0f);

    //Card backgrounds and energy orbs
    private static final String ATTACK_BLACK = "blackbeard/img/512/bg_attack_black.png";
    private static final String SKILL_BLACK = "blackbeard/img/512/bg_skill_black.png";
    private static final String POWER_BLACK = "blackbeard/img/512/bg_power_black.png";
    private static final String ENERGY_ORB_BLACK = "blackbeard/img/512/card_red_orb.png";

    private static final String ATTACK_BLACK_PORTRAIT = "blackbeard/img/1024/bg_attack_black.png";
    private static final String SKILL_BLACK_PORTRAIT = "blackbeard/img/1024/bg_skill_black.png";
    private static final String POWER_BLACK_PORTRAIT = "blackbeard/img/1024/bg_power_black.png";
    private static final String ENERGY_ORB_BLACK_PORTRAIT = "blackbeard/img/1024/card_red_orb.png";

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

    //Languages
    private static final String DEFAULT_LANGUAGE_FOLDER = "eng";
    private static final String POLISH_LANGUAGE_FOLDER = "pol";
    private static final String RUSSIAN_LANGUAGE_FOLDER = "rus";

    //Keywords
    public static Map<String, Keyword> keywords = new HashMap<>();
    public static final String WEAPON_KEYWORD = "blackbeard:WeaponKeyword";
    public static final String CANNONBALL_KEYWORD = "blackbeard:CannonballKeyword";
    public static final String RESISTANCE_KEYWORD = "blackbeard:ResistanceKeyword";

    //Sounds
    public static final String SOUND_YARR_KEY = "BLACKBEARD_YARR";
    public static final String SOUND_YARR_FILEPATH = getSoundFilePath("yarr");

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

    public static String getSoundFilePath(String soundName) {
        return "blackbeard/sounds/" + soundName + ".wav";
    }

    public static void initialize() {
        logger.info("========================= THE BLACKBEARD INIT =========================");

        new TheBlackbeardMod();

        logger.info("=======================================================================");
    }

    @Override
    public void receivePostInitialize() {
        //Mod settings
        Texture badgeTexture = ImageMaster.loadImage(BADGE_IMG);
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MOD_NAME, AUTHOR, DESCRIPTION, settingsPanel);

        //Events
        BaseMod.addEvent(SsssserpentBlackbeardEvent.ID, SsssserpentBlackbeardEvent.class, Exordium.ID);
        BaseMod.addEvent(VampiresBlackbeardEvent.ID, VampiresBlackbeardEvent.class, TheCity.ID);

        //Potions
        BaseMod.addPotion(BloodPotion.class, Color.WHITE.cpy(), Color.LIGHT_GRAY.cpy(), null, "blackbeard:BloodPotion", PlayerClassEnum.BLACKBEARD_CLASS);
        BaseMod.addPotion(GhostInAJar.class, Color.WHITE.cpy(), Color.LIGHT_GRAY.cpy(), null, "blackbeard:GhostInAJar", PlayerClassEnum.BLACKBEARD_CLASS);
        Color rumColor = new Color(211 / 255.0F, 102 / 255.0F, 0, 1);
        Color rumShadeColor = new Color(160 / 255.0F, 77 / 255.0F, 0, 1);
        BaseMod.addPotion(RumPotion.class, rumColor.cpy(), rumShadeColor.cpy(), null, "blackbeard:RumPotion", PlayerClassEnum.BLACKBEARD_CLASS);
        BaseMod.addPotion(ToastPotion.class, rumColor.cpy(), rumShadeColor.cpy(), null, "blackbeard:ToastPotion", PlayerClassEnum.BLACKBEARD_CLASS);
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

        //Add relics
        BaseMod.addRelicToCustomPool(new LoadTheCannons(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new BloodOrange(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new HipFlask(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new TreasureChest(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new WhitePearl(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new CannonballsOfSteel(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new GoldenRing(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new MagicalCauldron(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new Penknife(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new PoorMathSkills(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new PowderCan(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new Soberminded(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new Spearhead(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new CrossedSwords(), CardColorEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new KarateGi(), CardColorEnum.BLACKBEARD_BLACK);

        logger.info("Done editing relics");
    }

    @Override
    public void receiveEditCards() {
        logger.info("Begin editing cards");
        logger.info("Add cards for " + PlayerClassEnum.BLACKBEARD_CLASS.toString());

        BaseMod.addDynamicVariable(new MagicNumberPlusOneVariable());
        BaseMod.addDynamicVariable(new SecondMagicNumberVariable());

        BaseMod.addCard(new StrikeBlackbeard());
        BaseMod.addCard(new DefendBlackbeard());
        BaseMod.addCard(new Cutlass());
        BaseMod.addCard(new Upgrade());
        BaseMod.addCard(new Sharpening());
        BaseMod.addCard(new RearmingStrike());
        BaseMod.addCard(new Fencing());
        BaseMod.addCard(new Parrot());
        BaseMod.addCard(new TheDrunkenSailor());
        BaseMod.addCard(new Halberd());
        BaseMod.addCard(new SwordDance());
        BaseMod.addCard(new CannonBarrage());
        BaseMod.addCard(new Rum());
        BaseMod.addCard(new GhostInTheRum());
        BaseMod.addCard(new GoldenStrike());
        BaseMod.addCard(new PowderKeg());
        BaseMod.addCard(new Scrap());
        BaseMod.addCard(new RemoveScurvy());
        BaseMod.addCard(new InfiniteBarrage());
        BaseMod.addCard(new PirateStrength());
        BaseMod.addCard(new HumongousCannonball());
        BaseMod.addCard(new GoldenCannonball());
        BaseMod.addCard(new FinalBarrage());
        BaseMod.addCard(new BrownPants());
        BaseMod.addCard(new JollyRoger());
        BaseMod.addCard(new CaptainsCabin());
        BaseMod.addCard(new GoldenDefense());
        BaseMod.addCard(new Shrapnel());
        BaseMod.addCard(new Toast());
        BaseMod.addCard(new CatONineTails());
        BaseMod.addCard(new PirateEarring());
        BaseMod.addCard(new Murder());
        BaseMod.addCard(new Bandana());
        BaseMod.addCard(new TacticalRetreat());
        BaseMod.addCard(new Dagger());
        BaseMod.addCard(new PiratesWill());
        BaseMod.addCard(new WoodenLeg());
        BaseMod.addCard(new Eyepatch());
        BaseMod.addCard(new CaptainsHat());
        BaseMod.addCard(new BlindAttacks());
        BaseMod.addCard(new Boarding());
        BaseMod.addCard(new AgileStrike());
        BaseMod.addCard(new DangerousBlow());
        BaseMod.addCard(new VengefulSpirit());
        BaseMod.addCard(new Revenge());
        BaseMod.addCard(new GoldenRain());
        BaseMod.addCard(new Sword());
        BaseMod.addCard(new BeatUp());
        BaseMod.addCard(new IntimidatingStrike());
        BaseMod.addCard(new WiseDefense());
        BaseMod.addCard(new ChaoticDefense());
        BaseMod.addCard(new SmithingHammer());
        BaseMod.addCard(new Spear());
        BaseMod.addCard(new WeaponMastery());
        BaseMod.addCard(new Salvager());
        BaseMod.addCard(new Anchor());
        BaseMod.addCard(new BoostMorale());
        BaseMod.addCard(new MegaUpgrade());
        BaseMod.addCard(new Recklessness());
        BaseMod.addCard(new DelayedPain());
        BaseMod.addCard(new ArmorUp());
        BaseMod.addCard(new Intoxication());
        BaseMod.addCard(new CannonballSupply());
        BaseMod.addCard(new BountyHunter());
        BaseMod.addCard(new GoldenGuillotine());
        BaseMod.addCard(new DoubleBottom());
        BaseMod.addCard(new Lifeboat());
        BaseMod.addCard(new TerrorOfTheSeas());
        BaseMod.addCard(new Provisioning());
        BaseMod.addCard(new FishingNets());
        BaseMod.addCard(new BuriedTreasure());
        BaseMod.addCard(new DoubleCannonball());
        BaseMod.addCard(new CloakAndCannonball());
        BaseMod.addCard(new UndeadForm());
        BaseMod.addCard(new WeaponProficiency());
        BaseMod.addCard(new FieryDefense());
        BaseMod.addCard(new ReapersScythe());

        //Special cards are registered, but they are not shown in the library, because of DoNotShowSpecialCardsInLibraryPatch.
        BaseMod.addCard(new Cannonball());
        BaseMod.addCard(new SwordOfChaos());
        BaseMod.addCard(new SwordOfWisdom());
        BaseMod.addCard(new SwordOfFire());
        BaseMod.addCard(new VampiricScepter());

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

        keywords = gson.fromJson(keywordStrings, typeToken);

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
}
