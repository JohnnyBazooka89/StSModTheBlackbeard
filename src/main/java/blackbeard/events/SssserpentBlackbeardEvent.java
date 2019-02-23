package blackbeard.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.exordium.Sssserpent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;

public class SssserpentBlackbeardEvent extends AbstractImageEvent {
    public static final String ID = "blackbeard:Sssserpent";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final String[] OPTIONS;
    private static final String DIALOG_1;
    private static final String AGREE_DIALOG;
    private static final String DISAGREE_DIALOG;
    private static final String GOLD_RAIN_MSG;
    private SssserpentBlackbeardEvent.CUR_SCREEN screen;
    private static final int GOLD_REWARD = 225;
    private static final int A_2_GOLD_REWARD = 200;
    private int goldReward;
    private AbstractRelic relic;

    @Override
    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_SERPENT");
        }
    }

    public SssserpentBlackbeardEvent() {
        super(NAME, DIALOG_1, "images/events/liarsGame.jpg");
        this.screen = SssserpentBlackbeardEvent.CUR_SCREEN.INTRO;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.goldReward = A_2_GOLD_REWARD;
        } else {
            this.goldReward = GOLD_REWARD;
        }

        this.relic = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());

        this.imageEventText.setDialogOption("[Yarr!!!] #gGain #g" + goldReward + " #gGold. #gGain #ga #gRelic. You have no doubt in your mind.");
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                if (buttonPressed == 0) {
                    this.imageEventText.updateBodyText(AGREE_DIALOG);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                    this.screen = SssserpentBlackbeardEvent.CUR_SCREEN.YARR;
                    AbstractEvent.logMetricGainGoldAndRelic(ID, "YARR", this.relic, this.goldReward);
                } else if (buttonPressed == 1) {
                    this.imageEventText.updateBodyText(DISAGREE_DIALOG);
                    this.imageEventText.removeDialogOption(1);
                    this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                    this.screen = SssserpentBlackbeardEvent.CUR_SCREEN.DISAGREE;
                    AbstractEvent.logMetricIgnored(ID);
                }
                break;
            case YARR:
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, relic);
                AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldReward));
                AbstractDungeon.player.gainGold(this.goldReward);
                this.imageEventText.updateBodyText(GOLD_RAIN_MSG);
                this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                this.screen = SssserpentBlackbeardEvent.CUR_SCREEN.COMPLETE;
                break;
            default:
                this.openMap();
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(Sssserpent.ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        AGREE_DIALOG = DESCRIPTIONS[1];
        DISAGREE_DIALOG = DESCRIPTIONS[2];
        GOLD_RAIN_MSG = DESCRIPTIONS[3];
    }

    private enum CUR_SCREEN {
        INTRO,
        YARR,
        DISAGREE,
        COMPLETE
    }
}
