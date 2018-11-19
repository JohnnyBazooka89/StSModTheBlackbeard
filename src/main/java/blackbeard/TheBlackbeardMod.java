package blackbeard;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import blackbeard.cards.*;
import blackbeard.characters.TheBlackbeard;
import blackbeard.enums.AbstractCardEnum;
import blackbeard.enums.TheBlackbeardEnum;
import blackbeard.relics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class TheBlackbeardMod implements PostInitializeSubscriber,
        EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber,
        EditStringsSubscriber, EditKeywordsSubscriber {

    public static final Logger logger = LogManager.getLogger(TheBlackbeardMod.class.getName());

    //Default cards and orbs
    public static final String DEFAULT_POWER_CARD_ID = "blackbeard:BetaPower";
    public static final String DEFAULT_SKILL_CARD_ID = "blackbeard:BetaSkill";
    public static final String DEFAULT_ATTACK_CARD_ID = "blackbeard:Beta";
    public static final String DEFAULT_WEAPON_ORB_ID = "blackbeard:WeaponOrb";

    //Mod metadata
    private static final String MODNAME = "The Blackbeard";
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

    //Blackbeard assets
    private static final String BLACKBEARD_BUTTON = "blackbeard/img/charSelect/blackbeardButton.png";
    private static final String BLACKBEARD_PORTRAIT = "blackbeard/img/charSelect/blackbeardPortrait.jpg";

    //Badge
    private static final String BADGE_IMG = "blackbeard/img/ModBadge.png";

    //Localization strings
    private static final String RELIC_STRINGS_PATH = "blackbeard/localization/TheBlackbeard-RelicStrings.json";
    private static final String CARD_STRINGS_PATH = "blackbeard/localization/TheBlackbeard-CardStrings.json";
    private static final String POWER_STRINGS_PATH = "blackbeard/localization/TheBlackbeard-PowerStrings.json";
    private static final String ORB_STRINGS_PATH = "blackbeard/localization/TheBlackbeard-OrbStrings.json";

    public TheBlackbeardMod() {
        BaseMod.subscribe(this);

        logger.info("Creating the color " + AbstractCardEnum.BLACKBEARD_BLACK.toString());

        BaseMod.addColor(AbstractCardEnum.BLACKBEARD_BLACK,
                BLACK,
                ATTACK_BLACK, SKILL_BLACK, POWER_BLACK,
                ENERGY_ORB_BLACK,
                ATTACK_BLACK_PORTRAIT, SKILL_BLACK_PORTRAIT, POWER_BLACK_PORTRAIT,
                ENERGY_ORB_BLACK_PORTRAIT, ENERGY_ORB_IN_DESCRIPTION);

    }

    public static String getCardImagePath(String cardID) {
        return "blackbeard/img/cards/" + cardID.replaceFirst("blackbeard:", "") + ".png";
    }

    public static String getRelicImagePath(String cardID) {
        return "blackbeard/img/relics/" + cardID.replaceFirst("blackbeard:", "") + ".png";
    }

    public static String getRelicOutlineImagePath(String cardID) {
        return "blackbeard/img/relics/outlines/" + cardID.replaceFirst("blackbeard:", "") + ".png";
    }

    public static String getOrbImagePath(String cardID) {
        return "blackbeard/img/orbs/" + cardID.replaceFirst("blackbeard:", "") + ".png";
    }

    public static void initialize() {
        logger.info("========================= THE BLACKBEARD INIT =========================");

        new TheBlackbeardMod();

        logger.info("=======================================================================");
    }

    @Override
    public void receivePostInitialize() {
        // Mod badge
        Texture badgeTexture = new Texture(BADGE_IMG);
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Begin editing characters");
        logger.info("Add " + TheBlackbeardEnum.BLACKBEARD_CLASS.toString());

        BaseMod.addCharacter(
                new TheBlackbeard("The Blackbeard", TheBlackbeardEnum.BLACKBEARD_CLASS),
                BLACKBEARD_BUTTON,
                BLACKBEARD_PORTRAIT,
                TheBlackbeardEnum.BLACKBEARD_CLASS
        );

        logger.info("Done editing characters");
    }


    @Override
    public void receiveEditRelics() {
        logger.info("Begin editing relics");

        // Add relics
        BaseMod.addRelicToCustomPool(new LoadTheCannons(), AbstractCardEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new BloodOrange(), AbstractCardEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new HipFlask(), AbstractCardEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new TreasureChest(), AbstractCardEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new WhitePearl(), AbstractCardEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new CannonballsOfSteel(), AbstractCardEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new GoldenRing(), AbstractCardEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new MagicalCauldron(), AbstractCardEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new Penknife(), AbstractCardEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new PoorMathSkills(), AbstractCardEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new PowderCan(), AbstractCardEnum.BLACKBEARD_BLACK);
        BaseMod.addRelicToCustomPool(new Soberminded(), AbstractCardEnum.BLACKBEARD_BLACK);

        logger.info("Done editing relics");
    }

    @Override
    public void receiveEditCards() {
        logger.info("Begin editing cards");
        logger.info("Add cards for " + TheBlackbeardEnum.BLACKBEARD_CLASS.toString());

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
        //BaseMod.addCard(new Cannonball());
        BaseMod.addCard(new CannonBarrage());
        BaseMod.addCard(new Rum());
        BaseMod.addCard(new GhostInTheRum());
        BaseMod.addCard(new GoldenStrike());
        BaseMod.addCard(new PowderKeg());
        BaseMod.addCard(new Scrap());
        BaseMod.addCard(new RemoveScurvy());
        BaseMod.addCard(new InfiniteBarrage());
        //BaseMod.addCard(new SwordOfChaos());
        //BaseMod.addCard(new SwordOfWisdom());
        BaseMod.addCard(new PirateStrength());
        BaseMod.addCard(new HumongousCannonball());
        BaseMod.addCard(new GoldenCannonball());
        BaseMod.addCard(new FinalBarrage());
        BaseMod.addCard(new BrownPants());
        BaseMod.addCard(new JollyRoger());
        BaseMod.addCard(new CaptainsCabin());
        BaseMod.addCard(new GoldenDefend());
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
        BaseMod.addCard(new Keelhauling());
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
        BaseMod.addCard(new WiseDefend());
        BaseMod.addCard(new ChaoticDefend());
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
        //BaseMod.addCard(new RunForYourLife());
        BaseMod.addCard(new FishingNets());
        BaseMod.addCard(new BuriedTreasure());
        //BaseMod.addCard(new SwordOfFire());
        BaseMod.addCard(new DoubleCannonball());
        BaseMod.addCard(new DaggerAccumulation());
        BaseMod.addCard(new CloakAndCannonball());

        logger.info("Done editing cards");
    }

    @Override
    public void receiveEditStrings() {
        logger.info("Begin editing strings");

        //Relic Strings
        BaseMod.loadCustomStringsFile(RelicStrings.class, RELIC_STRINGS_PATH);
        //Card Strings
        BaseMod.loadCustomStringsFile(CardStrings.class, CARD_STRINGS_PATH);
        //Power Strings
        BaseMod.loadCustomStringsFile(PowerStrings.class, POWER_STRINGS_PATH);
        //Orb Strings
        BaseMod.loadCustomStringsFile(OrbStrings.class, ORB_STRINGS_PATH);

        logger.info("Done editing strings");
    }

    @Override
    public void receiveEditKeywords() {
        logger.info("Setting up custom keywords");

        BaseMod.addKeyword(new String[]{"weapon", "weapons"}, "Whenever you play an attack card, one Durability of your rightmost Weapon is used and card's damage is increased by Weapon's attack.");
        BaseMod.addKeyword(new String[]{"cannonball", "cannonballs"}, "Cannonballs are 0 cost attacks that deal 8 (10) damage and exhaust.");
        BaseMod.addKeyword(new String[]{"resistance"}, "You take less damage from enemy attacks. (Enemy intents always show actual incoming damage, with Resistance taken into account.) Resistance can be negative.");
    }

}
