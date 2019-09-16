package blackbeard.powers;

import blackbeard.TheBlackbeardMod;
import blackbeard.effects.DamageCurvy;
import blackbeard.effects.DamageLine;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ReapersMarkPower extends AbstractPower {
    public static final String POWER_ID = "blackbeard:ReapersMarkPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture power128Image = TextureLoader.getTexture(TheBlackbeardMod.getPower128ImagePath(POWER_ID));
    private static final Texture power48Image = TextureLoader.getTexture(TheBlackbeardMod.getPower48ImagePath(POWER_ID));

    protected float offset = MathUtils.random(-180.0F, 180.0F);

    public ReapersMarkPower(AbstractCreature owner, int amount) {
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
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 3) {
            for (int i = 0; i < 36; i++) {
                AbstractDungeon.effectList.add(new DamageLine(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(0.552F, 0F, 0.819F, 1), ((10 * i) + MathUtils.random(-10, 10) + offset)));
                if (i % 2 == 0) {
                    AbstractDungeon.effectList.add(new DamageCurvy(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(0.552F, 0F, 0.819F, 1)));
                }
            }
            int damage = (this.owner.maxHealth - this.owner.currentHealth) * 4 / 10;
            AbstractDungeon.actionManager.addToTop(new LoseHPAction(this.owner, this.owner, damage, AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 3));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}