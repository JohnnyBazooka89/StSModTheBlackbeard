package blackbeard.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import static blackbeard.TheBlackbeardMod.makeID;

public class GoldToLootPower extends AbstractPower {
    public static final String POWER_ID = makeID("GoldToLootPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int goldRemaining;

    public GoldToLootPower(AbstractCreature owner, int goldRemaining) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 5;
        this.goldRemaining = goldRemaining;
        this.loadRegion("thievery");
        this.type = PowerType.BUFF;
        this.updateDescription();
    }

    @Override
    public void onAttack(final DamageInfo damageInfo, final int n, final AbstractCreature target) {
        if (damageInfo.type == DamageInfo.DamageType.NORMAL) {
            AbstractPlayer player = AbstractDungeon.player;

            int amountOfGoldToGain = this.amount;
            if (amountOfGoldToGain > this.goldRemaining) {
                amountOfGoldToGain = this.goldRemaining;
            }
            this.goldRemaining -= amountOfGoldToGain;

            CardCrawlGame.sound.play("GOLD_JINGLE");
            AbstractDungeon.player.gainGold(amountOfGoldToGain);
            for (int i = 0; i < amountOfGoldToGain; i++) {
                AbstractDungeon.effectList.add(new GainPennyEffect(player, target.hb.cX, target.hb.cY, player.hb.cX, player.hb.cY, true));
            }

            if (this.goldRemaining <= 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }

            updateDescription();
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += 5;
        this.goldRemaining += stackAmount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.goldRemaining + DESCRIPTIONS[2];
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);

        if (this.goldRemaining > 0) {
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont,
                    Integer.toString(this.goldRemaining), x, y + 15 * Settings.scale, fontScale, c);
        }
    }
}