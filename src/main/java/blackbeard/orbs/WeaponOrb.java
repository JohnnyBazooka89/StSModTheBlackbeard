package blackbeard.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import org.apache.commons.lang3.StringUtils;

public abstract class WeaponOrb extends AbstractOrb {

    protected int attack;
    protected int durability;
    protected String rawDescription;
    private String imagePath;

    public WeaponOrb(String id, String name, String rawDecription, String imagePath, int attack, int durability) {
        this.ID = id;
        this.name = name;
        this.imagePath = imagePath;
        this.img = ImageMaster.loadImage(this.imagePath);
        this.attack = attack;
        this.durability = durability;
        this.rawDescription = rawDecription;
        this.updateDescription();
        this.channelAnimTimer = 0.5F;
    }

    @Override
    public void updateDescription() {
        this.applyFocus();
        String descriptionToSet;
        descriptionToSet = rawDescription;
        if(StringUtils.isNotEmpty(descriptionToSet)) {
            descriptionToSet += " NL ";
        }
        descriptionToSet += "Attack: " + attack + " NL " + "Durability: " + durability;
        this.description = descriptionToSet;
    }

    @Override
    public void onEvoke() {
        //Do nothing
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        this.renderText(sb);
        this.hb.render(sb);
    }

    @Override
    protected void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.attack), this.cX + NUM_X_OFFSET - 40.0F * Settings.scale, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, this.c, this.fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.durability), this.cX + NUM_X_OFFSET + 4.0F * Settings.scale, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
    }

    @Override
    public void playChannelSFX() {
        //Do nothing
    }

    public AbstractOrb makeCopy() {
        try {
            return this.getClass().getDeclaredConstructor(int.class, int.class).newInstance(attack, durability);
        } catch (Exception e) {
            throw new RuntimeException("WeaponOrb failed to auto-generate makeCopy for orb: " + this.ID);
        }
    }

    public int getAttack() {
        return attack;
    }

    public int getDurability() {
        return durability;
    }

    public void use(AbstractCreature target) {
        durability--;
        effectOnUse(target);
        updateDescription();
    }

    public void upgrade(int attackUpgrade, int durabilityUpgrade) {
        attack += attackUpgrade;
        durability += durabilityUpgrade;
        updateDescription();
    }

    public void setDurabilityToZero() {
        durability = 0;
        updateDescription();
    }

    public void effectOnUse(AbstractCreature target) {
        //Override to get effect on use
    }

    public void effectOnDestroy(){
        //Override to get effect on destroy
    }
}

