package blackbeard.characters;

import basemod.abstracts.CustomPlayer;
import blackbeard.cards.Cutlass;
import blackbeard.cards.DefendBlackbeard;
import blackbeard.cards.StrikeBlackbeard;
import blackbeard.patches.AbstractCardEnum;
import blackbeard.patches.TheBlackbeardEnum;
import blackbeard.relics.LoadTheCannons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

public class TheBlackbeard extends CustomPlayer {

    private static final int ENERGY_PER_TURN = 3;

    private static final String BLACKBEARD_SKELETON_ATLAS_PATH = "img/char/blackbeard/idle/skeleton.atlas";
    private static final String BLACKBEARD_SKELETON_JSON_PATH = "img/char/blackbeard/idle/skeleton.json";
    private static final String BLACKBEARD_SHOULDER_1 = "img/char/blackbeard/shoulder.png";
    private static final String BLACKBEARD_SHOULDER_2 = "img/char/blackbeard/shoulder2.png";
    private static final String BLACKBEARD_CORPSE = "img/char/blackbeard/corpse.png";

    public static final String DEFAULT_POWER_CARD_ID = "blackbeard:BetaPower";
    public static final String DEFAULT_SKILL_CARD_ID = "blackbeard:BetaSkill";
    public static final String DEFAULT_ATTACK_CARD_ID = "blackbeard:Beta";
    public static final String DEFAULT_WEAPON_ORB_ID = "blackbeard:WeaponOrb";

    public TheBlackbeard(String name, PlayerClass setClass) {
        super(name, setClass, null, null, (String) null, null);

        initializeClass(null, BLACKBEARD_SHOULDER_2, BLACKBEARD_SHOULDER_1, BLACKBEARD_CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

        this.loadAnimation(BLACKBEARD_SKELETON_ATLAS_PATH, BLACKBEARD_SKELETON_JSON_PATH, 1.0f);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> startingDeck = new ArrayList<>();
        startingDeck.add(StrikeBlackbeard.ID);
        startingDeck.add(StrikeBlackbeard.ID);
        startingDeck.add(StrikeBlackbeard.ID);
        startingDeck.add(StrikeBlackbeard.ID);
        startingDeck.add(StrikeBlackbeard.ID);
        startingDeck.add(DefendBlackbeard.ID);
        startingDeck.add(DefendBlackbeard.ID);
        startingDeck.add(DefendBlackbeard.ID);
        startingDeck.add(DefendBlackbeard.ID);
        startingDeck.add(Cutlass.ID);

        return startingDeck;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> startingRelics = new ArrayList<>();
        startingRelics.add(LoadTheCannons.ID);
        UnlockTracker.markRelicAsSeen(LoadTheCannons.ID);
        return startingRelics;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo("The Blackbeard",
                "After following his dusty map, he found himself trying to Slay the Spire. NL " +
                        "Skilled in using weapons, he wants all the treasures for himself.",
                75, 75, 0, 99, 5,
                this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return "The Blackbeard";
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.BLACKBEARD_BLACK;
    }

    @Override
    public Color getCardRenderColor() {
        return Color.BLACK;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Cutlass();
    }

    @Override
    public Color getCardTrailColor() {
        return Color.BLACK;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_HEAVY", MathUtils.random(-0.2f, 0.2f));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    @Override
    public String getLocalizedCharacterName() {
        return "The Blackbeard";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheBlackbeard("The Blackbeard", TheBlackbeardEnum.BLACKBEARD_CLASS);
    }

    @Override
    public String getSpireHeartText() {
        return "NL You ready your Weapon...";
    }

    @Override
    public Color getSlashAttackColor() {
        return Color.BLACK;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getVampireText() {
        return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. As you approach, they turn to you in eerie unison. The tallest among them bares fanged teeth and extends a long, pale hand towards you. NL ~\"Join~ ~us,~ ~oh Great Captain,~ ~and~ ~feel~ ~the~ ~warmth~ ~of~ ~the~ ~Spire.\"~";
    }

}
