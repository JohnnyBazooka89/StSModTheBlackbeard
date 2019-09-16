package blackbeard.orbs;

import blackbeard.powers.ArmorUpPower;
import blackbeard.powers.SalvagerPower;
import blackbeard.relics.KarateGi;
import blackbeard.relics.Spearhead;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractWeaponOrb extends AbstractOrb {

    protected int attack;
    protected int durability;
    protected String rawDescription;
    protected boolean justAddedUsingAttackCard;
    private String imagePath;

    protected float offset = MathUtils.random(-180.0F, 180.0F);

    public static final String ABSTRACT_WEAPON_ORB_ID = "blackbeard:AbstractWeaponOrb";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ABSTRACT_WEAPON_ORB_ID);

    public AbstractWeaponOrb(String id, String name, String rawDecription, String imagePath, int attack, int durability, boolean justAddedUsingAttackCard) {
        this.ID = id;
        this.name = name;
        this.imagePath = imagePath;
        this.img = TextureLoader.getTexture(this.imagePath);
        this.attack = attack;
        this.durability = durability;
        this.justAddedUsingAttackCard = justAddedUsingAttackCard;
        this.rawDescription = rawDecription;
        this.updateDescription();
        this.channelAnimTimer = 0.5F;
    }

    @Override
    public void updateDescription() {
        this.applyFocus();
        String descriptionToSet;
        descriptionToSet = rawDescription;
        if (StringUtils.isNotEmpty(descriptionToSet)) {
            descriptionToSet += orbStrings.DESCRIPTION[0];
        }
        descriptionToSet += orbStrings.DESCRIPTION[1] + attack + orbStrings.DESCRIPTION[2] + orbStrings.DESCRIPTION[3] + durability;
        this.description = descriptionToSet;
    }

    @Override
    public void onEvoke() {
        if (AbstractDungeon.player.hasPower(SalvagerPower.POWER_ID)) {
            SalvagerPower salvagerPower = (SalvagerPower) AbstractDungeon.player.getPower(SalvagerPower.POWER_ID);
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(salvagerPower.amount));
            salvagerPower.flash();
        }
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
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.attack), this.cX + NUM_X_OFFSET - 44.0F * Settings.scale,
                this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, this.c, this.fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, getDurabilityToDisplay(), this.cX + NUM_X_OFFSET + 8.0F * Settings.scale,
                this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
    }

    @Override
    public void playChannelSFX() {
        //Do nothing
    }

    public AbstractWeaponOrb makeCopy() {
        try {
            return this.getClass().getDeclaredConstructor(int.class, int.class, boolean.class).newInstance(attack, durability, justAddedUsingAttackCard);
        } catch (Exception e) {
            throw new RuntimeException("AbstractWeaponOrb failed to auto-generate makeCopy for orb: " + this.ID);
        }
    }

    public int getAttack() {
        return attack;
    }

    public int getDurability() {
        return durability;
    }

    public int getDurabilityForCombat() {
        if (AbstractDungeon.player.hasRelic(KarateGi.ID)) {
            return Integer.MAX_VALUE;
        }
        return durability;
    }

    public String getDurabilityToDisplay() {
        int durabilityForCombat = getDurabilityForCombat();
        if (durabilityForCombat == Integer.MAX_VALUE) {
            return "inf";
        }
        return Integer.toString(durabilityForCombat);
    }

    public int getDamageToDeal() {
        int additionalAttack = 0;
        if (AbstractDungeon.player.hasRelic(Spearhead.ID) && getDurabilityForCombat() == 1) {
            additionalAttack += 3;
        }
        return attack + additionalAttack;
    }

    public boolean isJustAddedUsingAttackCard() {
        return justAddedUsingAttackCard;
    }

    public void use(boolean triggerEffectOnUse) {
        if (!AbstractDungeon.player.hasRelic(KarateGi.ID)) {
            durability--;
        }
        if (AbstractDungeon.player.hasPower(ArmorUpPower.POWER_ID)) {
            ArmorUpPower armorUpPower = (ArmorUpPower) AbstractDungeon.player.getPower(ArmorUpPower.POWER_ID);
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, armorUpPower.amount));
            armorUpPower.flash();
        }
        if (triggerEffectOnUse) {
            effectOnUse();
        }
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

    public void clearJustAddedUsingAttackCard() {
        justAddedUsingAttackCard = false;
    }

    public void effectOnUse() {
        //method can be overridden in subclasses
    }

    public void effectAtStartOfTurnPostDraw() {
        //method can be overridden in subclasses
    }

    public void effectOnAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        //method can be overridden in subclasses
    }
}

