package blackbeard.events;

import basemod.eventUtil.util.Condition;
import blackbeard.TheBlackbeardMod;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Anchor;
import com.megacrit.cardcrawl.relics.CaptainsWheel;
import com.megacrit.cardcrawl.relics.HornCleat;

public class ShipwreckEvent extends AbstractImageEvent {

    public static final String EVENT_ID = "blackbeard:ShipwreckEvent";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private CurScreen screen = CurScreen.CHOICE;
    private int hpCost;
    private boolean hasAnchor;
    private boolean hasHornCleat;
    private boolean hasCaptainsWheel;
    private AbstractRelic anchor;
    private AbstractRelic hornCleat;
    private AbstractRelic captainsWheel;

    private enum CurScreen {CHOICE, RESULT}

    public ShipwreckEvent() {
        super(NAME, DESCRIPTIONS[0], TheBlackbeardMod.getEventImagePath(EVENT_ID));
        hpCost = AbstractDungeon.player.maxHealth / 10;
        setRelics();
        this.imageEventText.setDialogOption(!this.hasAnchor ? OPTIONS[0] + this.hpCost + OPTIONS[3] : OPTIONS[4], this.hasAnchor);
        this.imageEventText.setDialogOption(!this.hasHornCleat ? OPTIONS[1] + this.hpCost + OPTIONS[3] : OPTIONS[5], this.hasHornCleat);
        this.imageEventText.setDialogOption(!this.hasCaptainsWheel ? OPTIONS[2] + this.hpCost + OPTIONS[3] : OPTIONS[6], this.hasCaptainsWheel);
        this.imageEventText.setDialogOption(OPTIONS[7]);
    }

    private void setRelics() {
        this.hasAnchor = AbstractDungeon.player.hasRelic(Anchor.ID);
        this.hasHornCleat = AbstractDungeon.player.hasRelic(HornCleat.ID);
        this.hasCaptainsWheel = AbstractDungeon.player.hasRelic(CaptainsWheel.ID);
        this.anchor = new Anchor();
        this.hornCleat = new HornCleat();
        this.captainsWheel = new CaptainsWheel();
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
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, anchor);
                        takeDamage();
                        logMetricObtainRelic(EVENT_ID, Anchor.ID, anchor);
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, hornCleat);
                        takeDamage();
                        logMetricObtainRelic(EVENT_ID, HornCleat.ID, hornCleat);
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, captainsWheel);
                        takeDamage();
                        logMetricObtainRelic(EVENT_ID, CaptainsWheel.ID, captainsWheel);
                        break;
                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        logMetricIgnored(EVENT_ID);
                        break;
                }

                return;
        }
        openMap();
    }

    private void takeDamage() {
        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, this.hpCost, DamageInfo.DamageType.HP_LOSS));
    }

    public static Condition getCanBeEncounteredCondition() {
        return () -> !AbstractDungeon.player.hasRelic(Anchor.ID) || !AbstractDungeon.player.hasRelic(HornCleat.ID) || !AbstractDungeon.player.hasRelic(CaptainsWheel.ID);
    }

}
