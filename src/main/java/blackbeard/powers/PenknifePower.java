package blackbeard.powers;

import blackbeard.TheBlackbeardMod;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PenknifePower extends AbstractPower {
    public static final String POWER_ID = "blackbeard:PenknifePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture power128Image = TextureLoader.getTexture(TheBlackbeardMod.getPower128ImagePath(POWER_ID));
    private static final Texture power48Image = TextureLoader.getTexture(TheBlackbeardMod.getPower48ImagePath(POWER_ID));

    public PenknifePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(power128Image, 0, 0, power128Image.getWidth(), power128Image.getHeight());
        this.region48 = new TextureAtlas.AtlasRegion(power48Image, 0, 0, power48Image.getWidth(), power48Image.getHeight());
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.priority = 6;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL && AbstractDungeon.player.hasPower(WeaponPower.POWER_ID)) {
            return damage * 2.0F;
        }
        return damage;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && AbstractDungeon.player.hasPower(WeaponPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }
}