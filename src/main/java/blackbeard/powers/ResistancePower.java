package blackbeard.powers;

import blackbeard.TheBlackbeardMod;
import blackbeard.characters.TheBlackbeard;
import blackbeard.utils.BlackbeardAchievementUnlocker;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static blackbeard.TheBlackbeardMod.makeID;

public class ResistancePower extends AbstractPower {

    public static final String POWER_ID = makeID("ResistancePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture power128Image = TextureLoader.getTexture(TheBlackbeardMod.getPower128ImagePath(POWER_ID));
    private static final Texture power48Image = TextureLoader.getTexture(TheBlackbeardMod.getPower48ImagePath(POWER_ID));

    public ResistancePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(power128Image, 0, 0, power128Image.getWidth(), power128Image.getHeight());
        this.region48 = new TextureAtlas.AtlasRegion(power48Image, 0, 0, power48Image.getWidth(), power48Image.getHeight());
        this.type = PowerType.BUFF;
        this.canGoNegative = true;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount > 0) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
            this.type = PowerType.BUFF;
        } else {
            int tmp = -this.amount;
            this.description = DESCRIPTIONS[0] + tmp + DESCRIPTIONS[2] + DESCRIPTIONS[3];
            this.type = PowerType.DEBUFF;
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

        // Check if Resistance is 10 or more and unlock the achievement
        if (this.amount >= 10) {
            AbstractPlayer p = AbstractDungeon.player;
            if (p instanceof TheBlackbeard) {
                BlackbeardAchievementUnlocker.unlockAchievement("RESISTANT");
            }
        }
    }


    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage - this.amount;
        }
        return damage;
    }
}