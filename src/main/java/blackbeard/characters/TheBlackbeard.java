package blackbeard.characters;

import basemod.abstracts.CustomPlayer;
import blackbeard.cards.Cutlass;
import blackbeard.cards.DefendBlackbeard;
import blackbeard.cards.StrikeBlackbeard;
import blackbeard.patches.TheBlackbeardEnum;
import blackbeard.relics.LoadTheCannons;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.EnergyManager;
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

    public static ArrayList<String> getStartingDeck() {
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

    public static ArrayList<String> getStartingRelics() {
        ArrayList<String> startingRelics = new ArrayList<>();
        startingRelics.add(LoadTheCannons.ID);
        UnlockTracker.markRelicAsSeen(LoadTheCannons.ID);
        return startingRelics;
    }

    public static CharSelectInfo getLoadout() {
        return new CharSelectInfo("The Blackbeard",
                "After following his dusty map, he found himself trying to Slay the Spire. NL " +
                        "Skilled in using weapons, he wants all the treasures for himself.",
                75, 75, 0, 99, 5,
                TheBlackbeardEnum.BLACKBEARD_CLASS, getStartingRelics(), getStartingDeck(), false);
    }

}
