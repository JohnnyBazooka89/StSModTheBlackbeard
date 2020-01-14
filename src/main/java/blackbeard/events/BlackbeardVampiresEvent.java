package blackbeard.events;

import basemod.helpers.BaseModCardTags;
import blackbeard.cards.VampiricScepter;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Bite;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.BloodVial;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.List;

public class BlackbeardVampiresEvent extends AbstractImageEvent {
    public static final String ID = "blackbeard:Vampires";
    private static final EventStrings eventStrings;
    private static final EventStrings customEventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final String[] OPTIONS;
    private static final String ACCEPT_BODY;
    private static final String EXIT_BODY;
    private static final String GIVE_VIAL;
    private static final int HP_DRAIN_PERCENT = 30;
    private static final float HP_PERCENT = 0.7F;
    private static final int VAMPIRIC_SCEPTER_PRICE = 100;
    private int screenNum = 0;
    private boolean hasVial;
    private AbstractCard vampiricScepterCard;
    private List<String> bites;

    public BlackbeardVampiresEvent() {
        super(NAME, "test", "images/events/vampires.jpg");
        this.vampiricScepterCard = new VampiricScepter();
        this.body = AbstractDungeon.player.getVampireText();
        this.bites = new ArrayList<>();
        this.hasVial = AbstractDungeon.player.hasRelic("Blood Vial");
        this.imageEventText.setDialogOption(OPTIONS[0] + HP_DRAIN_PERCENT + OPTIONS[1], new Bite());
        if (this.hasVial) {
            String vialName = (new BloodVial()).name;
            this.imageEventText.setDialogOption(OPTIONS[3] + vialName + OPTIONS[4], new Bite());
        }
        if (AbstractDungeon.player.gold >= VAMPIRIC_SCEPTER_PRICE) {
            this.imageEventText.setDialogOption(customEventStrings.OPTIONS[0] + VAMPIRIC_SCEPTER_PRICE + customEventStrings.OPTIONS[1], vampiricScepterCard);
        } else {
            this.imageEventText.setDialogOption(customEventStrings.OPTIONS[2] + VAMPIRIC_SCEPTER_PRICE + customEventStrings.OPTIONS[3], true);
        }
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        CardCrawlGame.sound.play("EVENT_VAMP_BITE");
                        this.imageEventText.updateBodyText(ACCEPT_BODY);
                        int hpLoss = (int) ((float) AbstractDungeon.player.maxHealth * HP_PERCENT);
                        int diff = AbstractDungeon.player.maxHealth - hpLoss;
                        AbstractDungeon.player.maxHealth = hpLoss;
                        if (AbstractDungeon.player.maxHealth <= 0) {
                            AbstractDungeon.player.maxHealth = 1;
                        }

                        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
                            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
                        }

                        this.replaceAttacks();
                        logMetricObtainCardsLoseMapHP(Vampires.ID, "Became a vampire", this.bites, diff);
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 1:
                        if (this.hasVial) {
                            CardCrawlGame.sound.play("EVENT_VAMP_BITE");
                            this.imageEventText.updateBodyText(GIVE_VIAL);
                            AbstractDungeon.player.loseRelic("Blood Vial");
                            this.replaceAttacks();
                            logMetricObtainCardsLoseRelic(Vampires.ID, "Became a vampire (Vial)", this.bites, new BloodVial());
                            this.screenNum = 1;
                            this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                            this.imageEventText.clearRemainingOptions();
                        } else {
                            buyVampiricScepter();
                        }
                        return;
                    case 2:
                        if (this.hasVial) {
                            buyVampiricScepter();
                        } else {
                            refuse();
                        }
                        return;
                    default:
                        refuse();
                        return;
                }
            case 1:
                this.openMap();
                break;
            default:
                this.openMap();
        }

    }

    private void refuse() {
        logMetricIgnored(Vampires.ID);
        this.imageEventText.updateBodyText(EXIT_BODY);
        this.screenNum = 2;
        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
        this.imageEventText.clearRemainingOptions();
    }

    private void buyVampiricScepter() {
        CardCrawlGame.sound.play("EVENT_VAMP_BITE");
        this.imageEventText.updateBodyText(customEventStrings.DESCRIPTIONS[0]);
        AbstractDungeon.player.loseGold(VAMPIRIC_SCEPTER_PRICE);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(vampiricScepterCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

        List<String> temp = new ArrayList<>();
        temp.add(vampiricScepterCard.cardID);
        logMetric(ID, "Bought Vampiric Scepter", temp, null, null, null, null, null, null, 0, 0, 0, 0, 0, 125);

        this.screenNum = 1;
        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
        this.imageEventText.clearRemainingOptions();
    }

    private void replaceAttacks() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;

        for (int i = masterDeck.size() - 1; i >= 0; i--) {
            AbstractCard card = masterDeck.get(i);
            if (card.tags.contains(AbstractCard.CardTags.STARTER_STRIKE) || card.tags.contains(BaseModCardTags.BASIC_STRIKE)) {
                AbstractDungeon.player.masterDeck.removeCard(card);
            }
        }

        for (int i = 0; i < 5; i++) {
            Bite bite = new Bite();
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(bite, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            this.bites.add(bite.cardID);
        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(Vampires.ID);
        customEventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        ACCEPT_BODY = DESCRIPTIONS[2];
        EXIT_BODY = DESCRIPTIONS[3];
        GIVE_VIAL = DESCRIPTIONS[4];
    }
}

