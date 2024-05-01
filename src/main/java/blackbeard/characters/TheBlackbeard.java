package blackbeard.characters;

import basemod.abstracts.CustomPlayer;
import blackbeard.TheBlackbeardMod;
import blackbeard.cards.BlackbeardDefend;
import blackbeard.cards.BlackbeardStrike;
import blackbeard.cards.Cutlass;
import blackbeard.cards.IntimidatingStrike;
import blackbeard.enums.CardColorEnum;
import blackbeard.enums.PlayerClassEnum;
import blackbeard.relics.LoadTheCannons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.List;

public class TheBlackbeard extends CustomPlayer {

    private static final String ID = "blackbeard:BlackbeardCharacter";
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);

    private static final int ENERGY_PER_TURN = 3;
    private static final int STARTING_GOLD = 99;

    private static final String BLACKBEARD_SKELETON_ATLAS_PATH = "blackbeard/img/char/blackbeard/idle/skeleton.atlas";
    private static final String BLACKBEARD_SKELETON_JSON_PATH = "blackbeard/img/char/blackbeard/idle/skeleton.json";
    private static final String BLACKBEARD_SHOULDER_1 = "blackbeard/img/char/blackbeard/shoulder.png";
    private static final String BLACKBEARD_SHOULDER_2 = "blackbeard/img/char/blackbeard/shoulder2.png";
    private static final String BLACKBEARD_CORPSE = "blackbeard/img/char/blackbeard/corpse.png";

    private static final String CAPTAIN_ABE_ENCOUNTER_STRINGS_ID = "blackbeard:CaptainAbeEncounter";
    private static final String CHRISTMAS_THEME_GREETING_STRINGS_ID = "blackbeard:ChristmasThemeGreeting";
    private static final String MAXIMUM_WEAPONS_REACHED_STRINGS_ID = "blackbeard:MaximumWeaponsReached";
    private static final CharacterStrings captainAbeEncounterStrings = CardCrawlGame.languagePack.getCharacterString(CAPTAIN_ABE_ENCOUNTER_STRINGS_ID);
    private static final CharacterStrings christmasThemeGreetingStrings = CardCrawlGame.languagePack.getCharacterString(CHRISTMAS_THEME_GREETING_STRINGS_ID);
    @SuppressWarnings("unused") //Used in MaximumWeaponsReachedPatch
    public static final CharacterStrings maximumWeaponsReachedStrings = CardCrawlGame.languagePack.getCharacterString(MAXIMUM_WEAPONS_REACHED_STRINGS_ID);

    public TheBlackbeard(String name) {
        super(name, PlayerClassEnum.BLACKBEARD_CLASS, null, null, (String) null, null);

        initializeClass(null, BLACKBEARD_SHOULDER_2, BLACKBEARD_SHOULDER_1, BLACKBEARD_CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

        this.dialogX = (this.drawX + 30.0F * Settings.scale);
        this.dialogY = (this.drawY + 200.0F * Settings.scale);

        this.loadAnimation(BLACKBEARD_SKELETON_ATLAS_PATH, BLACKBEARD_SKELETON_JSON_PATH, 1.0f);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        CardCrawlGame.goldGained = STARTING_GOLD;
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> startingDeck = new ArrayList<>();
        startingDeck.add(BlackbeardStrike.ID);
        startingDeck.add(BlackbeardStrike.ID);
        startingDeck.add(BlackbeardStrike.ID);
        startingDeck.add(BlackbeardStrike.ID);
        startingDeck.add(IntimidatingStrike.ID);
        startingDeck.add(BlackbeardDefend.ID);
        startingDeck.add(BlackbeardDefend.ID);
        startingDeck.add(BlackbeardDefend.ID);
        startingDeck.add(BlackbeardDefend.ID);
        startingDeck.add(Cutlass.ID);

        return startingDeck;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> startingRelics = new ArrayList<>();
        startingRelics.add(LoadTheCannons.ID);
        UnlockTracker.markRelicAsSeen(LoadTheCannons.ID);
        return startingRelics;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(characterStrings.NAMES[0],
                characterStrings.TEXT[0],
                75, 75, 0, STARTING_GOLD, 5,
                this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[1];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CardColorEnum.BLACKBEARD_BLACK;
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
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheBlackbeard(characterStrings.NAMES[0]);
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
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
        return characterStrings.TEXT[2];
    }

    @Override
    public void applyStartOfCombatLogic() {
        super.applyStartOfCombatLogic();
        if (TheBlackbeardMod.shouldUseChristmasTheme() && AbstractDungeon.floorNum == 1) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, christmasThemeGreetingStrings.TEXT[0], 0.0F, 3.0F));
        }
        if (AbstractDungeon.lastCombatMetricKey.equals("Pondfish")) {
            int i = AbstractDungeon.cardRandomRng.random(0, captainAbeEncounterStrings.TEXT.length - 1);
            AbstractDungeon.actionManager.addToBottom(new SFXAction(TheBlackbeardMod.SOUND_YARR_KEY));
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, captainAbeEncounterStrings.TEXT[i], 0.0F, 3.0F));
        }
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(TheBlackbeardMod.getHeartPanelImagePath(1)));
        panels.add(new CutscenePanel(TheBlackbeardMod.getHeartPanelImagePath(2)));
        panels.add(new CutscenePanel(TheBlackbeardMod.getHeartPanelImagePath(3)));
        return panels;
    }
}
