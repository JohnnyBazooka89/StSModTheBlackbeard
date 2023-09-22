package blackbeard.powers;

import blackbeard.TheBlackbeardMod;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BleedPower extends AbstractPower implements HealthBarRenderPower {
    public static final String POWER_ID = "blackbeard:BleedPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Color bloodColor = new Color(102F / 255.0F, 0f, 0f, 1f);
    private static final Texture power128Image = TextureLoader.getTexture(TheBlackbeardMod.getPower128ImagePath(POWER_ID));
    private static final Texture power48Image = TextureLoader.getTexture(TheBlackbeardMod.getPower48ImagePath(POWER_ID));

    public BleedPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(power128Image, 0, 0, power128Image.getWidth(), power128Image.getHeight());
        this.region48 = new TextureAtlas.AtlasRegion(power48Image, 0, 0, power48Image.getWidth(), power48Image.getHeight());
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.owner, this.amount, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public int getHealthBarAmount() {
        return this.amount;
    }

    @Override
    public Color getColor() {
        return bloodColor;
    }
}