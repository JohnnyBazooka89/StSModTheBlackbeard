package blackbeard.events;

import blackbeard.TheBlackbeardMod;
import blackbeard.cards.BlackbeardDefend;
import blackbeard.cards.BlackbeardStrike;
import blackbeard.cards.GoldenDefense;
import blackbeard.cards.GoldenStrike;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.List;

public class GoldsmithEvent extends AbstractImageEvent {

    public static final String EVENT_ID = "blackbeard:GoldsmithEvent";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurScreen screen = CurScreen.CHOICE;
    private static int GOLD_COST = 50;
    private boolean hasStrike;
    private boolean hasDefend;
    private boolean hasEnoughGold;
    private boolean canGildStrike;
    private boolean canGildDefend;
    private AbstractCard goldenStrike;
    private AbstractCard goldenDefense;

    private enum CurScreen {CHOICE, RESULT;}

    public GoldsmithEvent() {
        super(NAME, DESCRIPTIONS[0], TheBlackbeardMod.getEventImagePath(EVENT_ID));
        setCards();
        if (!this.hasEnoughGold) {
            this.imageEventText.setDialogOption(OPTIONS[4], true);
            this.imageEventText.setDialogOption(OPTIONS[4], true);
        } else {
            if (this.hasStrike) {
                this.imageEventText.setDialogOption(getStrikeOptionText(), goldenStrike);
            } else {
                this.imageEventText.setDialogOption(OPTIONS[5], true);
            }
            if (this.hasDefend) {
                this.imageEventText.setDialogOption(getDefendOptionText(), goldenDefense);
            } else {
                this.imageEventText.setDialogOption(OPTIONS[6], true);
            }
        }
        this.imageEventText.setDialogOption(OPTIONS[7]);
    }

    public static String getStrikeOptionText() {
        return OPTIONS[0] + GOLD_COST + OPTIONS[1];
    }

    public static String getDefendOptionText() {
        return OPTIONS[2] + GOLD_COST + OPTIONS[3];
    }

    private void setCards() {
        this.hasEnoughGold = AbstractDungeon.player.gold >= GOLD_COST;
        this.hasStrike = CardHelper.hasCardWithID(BlackbeardStrike.ID);
        this.hasDefend = CardHelper.hasCardWithID(BlackbeardDefend.ID);
        this.goldenStrike = new GoldenStrike();
        this.goldenDefense = new GoldenDefense();
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case CHOICE:
                this.screen = CurScreen.RESULT;
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[8]);
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        gildACard(BlackbeardStrike.ID, goldenStrike);
                        AbstractDungeon.player.loseGold(GOLD_COST);
                        logMetricObtainCard(EVENT_ID, GoldenStrike.ID, goldenStrike);
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        gildACard(BlackbeardDefend.ID, goldenDefense);
                        AbstractDungeon.player.loseGold(GOLD_COST);
                        logMetricObtainCard(EVENT_ID, GoldenDefense.ID, goldenDefense);
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        logMetricIgnored(EVENT_ID);
                        break;
                }
                return;
        }
        openMap();
    }

    private void gildACard(String originalCardId, AbstractCard newCard) {
        List<AbstractCard> transformableCards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.getPurgeableCards().group) {
            if (c.cardID.equals(originalCardId)) {
                transformableCards.add(c);
            }
        }
        if (!transformableCards.isEmpty()) {
            AbstractCard cardToTransform = transformableCards.get(AbstractDungeon.miscRng.random(transformableCards.size() - 1));
            if (cardToTransform.upgraded) {
                newCard.upgrade();
            }
            AbstractDungeon.player.masterDeck.removeCard(cardToTransform);
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(
                    newCard, Settings.WIDTH * 0.5F, Settings.HEIGHT / 2.0F, false));
        }
    }

}
