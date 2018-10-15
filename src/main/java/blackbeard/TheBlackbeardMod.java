package blackbeard;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import blackbeard.cards.*;
import blackbeard.characters.TheBlackbeard;
import blackbeard.patches.AbstractCardEnum;
import blackbeard.patches.TheBlackbeardEnum;
import blackbeard.relics.LoadTheCannons;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.CultistMask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class TheBlackbeardMod implements PostInitializeSubscriber,
        EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber,
        EditStringsSubscriber, SetUnlocksSubscriber, EditKeywordsSubscriber, OnCardUseSubscriber {

    public static final Logger logger = LogManager.getLogger(TheBlackbeardMod.class.getName());

    private static final String MODNAME = "The Blackbeard";
    private static final String AUTHOR = "JohnnyBazooka89";
    private static final String DESCRIPTION = "Adds The Blackbeard as a new playable character.";

    private static final Color BLACK = CardHelper.getColor(0.0f, 0.0f, 0.0f);

    // card backgrounds
    private static final String ATTACK_BLACK = "img/512/bg_attack_black.png";
    private static final String SKILL_BLACK = "img/512/bg_skill_black.png";
    private static final String POWER_BLACK = "img/512/bg_power_black.png";
    private static final String ENERGY_ORB_BLACK = "img/512/card_red_orb.png";

    private static final String ATTACK_BLACK_PORTRAIT = "img/1024/bg_attack_black.png";
    private static final String SKILL_BLACK_PORTRAIT = "img/1024/bg_skill_black.png";
    private static final String POWER_BLACK_PORTRAIT = "img/1024/bg_power_black.png";
    private static final String ENERGY_ORB_BLACK_PORTRAIT = "img/1024/card_red_orb.png";

    private static final String ENERGY_ORB_IN_DESCRIPTION = "img/energy/energyOrbInDescription.png";

    // blackbeard assets
    private static final String BLACKBEARD_BUTTON = "img/charSelect/blackbeardButton.png";
    private static final String BLACKBEARD_PORTRAIT = "img/charSelect/blackbeardPortrait.jpg";

    // badge
    public static final String BADGE_IMG = "img/ModBadge.png";

    public TheBlackbeardMod() {
        BaseMod.subscribe(this);

        logger.info("creating the color " + AbstractCardEnum.BLACKBEARD_BLACK.toString());

        BaseMod.addColor(AbstractCardEnum.BLACKBEARD_BLACK,
                BLACK,
                ATTACK_BLACK, SKILL_BLACK, POWER_BLACK,
                ENERGY_ORB_BLACK,
                ATTACK_BLACK_PORTRAIT, SKILL_BLACK_PORTRAIT, POWER_BLACK_PORTRAIT,
                ENERGY_ORB_BLACK_PORTRAIT, ENERGY_ORB_IN_DESCRIPTION);

    }

    public static String getCardImagePath(String cardID) {
        return "img/cards/" + cardID.replaceFirst("blackbeard:", "") + ".png";
    }

    public static String getRelicImagePath(String cardID) {
        return "img/relics/" + cardID.replaceFirst("blackbeard:", "") + ".png";
    }

    public static String getOrbImagePath(String cardID) {
        return "img/orbs/" + cardID.replaceFirst("blackbeard:", "") + ".png";
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

        Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("begin editing characters");

        logger.info("add " + TheBlackbeardEnum.BLACKBEARD_CLASS.toString());
        BaseMod.addCharacter(
                new TheBlackbeard("The Blackbeard", TheBlackbeardEnum.BLACKBEARD_CLASS),
                AbstractCardEnum.BLACKBEARD_BLACK,
                BLACKBEARD_BUTTON,
                BLACKBEARD_PORTRAIT,
                TheBlackbeardEnum.BLACKBEARD_CLASS
        );

        logger.info("done editing characters");
    }


    @Override
    public void receiveEditRelics() {
        logger.info("begin editing relics");

        // Add relics
        BaseMod.addRelicToCustomPool(new LoadTheCannons(), AbstractCardEnum.BLACKBEARD_BLACK);

        logger.info("done editing relics");
    }

    @Override
    public void receiveEditCards() {
        logger.info("begin editing cards");

        logger.info("add cards for " + TheBlackbeardEnum.BLACKBEARD_CLASS.toString());

        BaseMod.addCard(new StrikeBlackbeard());
        BaseMod.addCard(new DefendBlackbeard());
        BaseMod.addCard(new Cutlass());
        BaseMod.addCard(new Upgrade());
        BaseMod.addCard(new Sharpening());
        BaseMod.addCard(new QuickSlash());
        BaseMod.addCard(new Fencing());
        BaseMod.addCard(new Parrot());
        BaseMod.addCard(new TheDrunkenSailor());
        BaseMod.addCard(new Halberd());
        BaseMod.addCard(new SwordDance());
        BaseMod.addCard(new Cannonball());
        BaseMod.addCard(new CannonBarrage());
        BaseMod.addCard(new Rum());
        BaseMod.addCard(new GhostInTheRum());
        BaseMod.addCard(new GoldenStrike());
        BaseMod.addCard(new PowderKeg());
        BaseMod.addCard(new Recycle());
        BaseMod.addCard(new RemoveScurvy());
        BaseMod.addCard(new InfiniteBarrage());
        BaseMod.addCard(new SwordOfChaos());
        BaseMod.addCard(new SwordOfWisdom());
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
        BaseMod.addCard(new IntimidatingBlow());
        BaseMod.addCard(new WiseDefend());
        BaseMod.addCard(new ChaoticDefend());
        BaseMod.addCard(new SmithingHammer());
        BaseMod.addCard(new Spear());

        logger.info("done editing cards");
    }

    @Override
    public void receiveEditStrings() {
        logger.info("begin editing strings");

        // RelicStrings
        String relicStrings = Gdx.files.internal("localization/TheBlackbeard-RelicStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        // CardStrings
        String cardStrings = Gdx.files.internal("localization/TheBlackbeard-CardStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        //PowerStrings
        String powerStrings = Gdx.files.internal("localization/TheBlackbeard-PowerStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        //OrbStrings
        String orbStrings = Gdx.files.internal("localization/TheBlackbeard-OrbStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(OrbStrings.class, orbStrings);

        logger.info("done editing strings");
    }

    @Override
    public void receiveSetUnlocks() {
    }

    @Override
    public void receiveEditKeywords() {
        logger.info("setting up custom keywords");
        BaseMod.addKeyword(new String[]{"weapon", "weapons"}, "Whenever you play an Attack card, remove 1 Durability from your rightmost Weapon and deal its Attack damage.");
        BaseMod.addKeyword(new String[]{"cannonball", "cannonballs"}, "Cannonballs are 0 cost attacks that deal 8 (12) damage and exhaust.");
        BaseMod.addKeyword(new String[]{"resistance"}, "You take less damage from enemy attacks.");
    }

    @Override
    public void receiveCardUsed(AbstractCard c) {
        if (AbstractDungeon.player.hasRelic(CultistMask.ID)) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "CAW", 1.0f, 2.0f));
        }
    }
}
