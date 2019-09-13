package blackbeard.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class GiantTextEffect extends AbstractGameEffect {
    private String targetString;

    public GiantTextEffect(float x, float y, String targetString) {
        this.targetString = targetString;
        this.x = x;
        this.y = y + 100.0F * Settings.scale;
        this.color = Color.WHITE.cpy();
        this.duration = 1.0F;
    }

    private float x;
    private float y;

    @Override
    public void update() {
        this.color.a = Interpolation.pow5Out.apply(0.0F, 0.8F, this.duration);
        this.color.a += MathUtils.random(-0.05F, 0.05F);
        this.color.a = MathUtils.clamp(this.color.a, 0.0F, 1.0F);

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, this.targetString, this.x, this.y, this.color, 2.5F - this.duration / 4.0F + MathUtils.random(0.05F));
        sb.setBlendFunction(770, 1);
        FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, this.targetString, this.x, this.y, this.color, 0.05F + 2.5F - this.duration / 4.0F + MathUtils.random(0.05F));
        sb.setBlendFunction(770, 771);
    }

    @Override
    public void dispose() {
    }
}
