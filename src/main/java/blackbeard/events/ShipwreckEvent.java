package blackbeard.events;

import blackbeard.TheBlackbeardMod;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Anchor;
import com.megacrit.cardcrawl.relics.HappyFlower;
import com.megacrit.cardcrawl.relics.Lantern;

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

    private enum CurScreen {CHOICE, RESULT;}

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
        this.hasHornCleat = AbstractDungeon.player.hasRelic(HappyFlower.ID); //TODO: Fix, when Watcher releases
        this.hasCaptainsWheel = AbstractDungeon.player.hasRelic(Lantern.ID); //TODO: Fix, when Watcher releases
        this.anchor = new Anchor();
        this.hornCleat = new HappyFlower(); //TODO: Fix, when Watcher releases
        this.captainsWheel = new Lantern(); //TODO: Fix, when Watcher releases
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
                        logMetricObtainRelic(EVENT_ID, HappyFlower.ID, hornCleat); //TODO: Fix, when Watcher releases
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, captainsWheel);
                        takeDamage();
                        logMetricObtainRelic(EVENT_ID, Lantern.ID, captainsWheel); //TODO: Fix, when Watcher releases
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

}
