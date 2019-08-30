package blackbeard.powers;

import basemod.ReflectionHacks;
import blackbeard.TheBlackbeardMod;
import blackbeard.enums.PlayerClassEnum;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.Skin;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.SlotData;
import com.esotericsoftware.spine.attachments.RegionAttachment;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ParrotPower extends AbstractPower {
    public static final String POWER_ID = "blackbeard:ParrotPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture power128Image = ImageMaster.loadImage(TheBlackbeardMod.getPower128ImagePath(POWER_ID));
    private static final Texture power48Image = ImageMaster.loadImage(TheBlackbeardMod.getPower48ImagePath(POWER_ID));

    private static final String BLACKBEARD_PARROT_PATH = "blackbeard/char/blackbeard/idle/Parrot.png";

    public CardQueueItem lastCardQueueItem = null;

    public ParrotPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.region128 = new TextureAtlas.AtlasRegion(power128Image, 0, 0, power128Image.getWidth(), power128Image.getHeight());
        this.region48 = new TextureAtlas.AtlasRegion(power48Image, 0, 0, power48Image.getWidth(), power48Image.getHeight());
        this.type = PowerType.BUFF;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        lastCardQueueItem = null;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //Logic mostly copied from Echo Power.
        //It uses ParrotPatch to work properly, because if logic is in overridden AbstractPower.atEndOfTurn method, it doesn't work at all.
        if (!card.purgeOnUse) {
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster) action.target;
            }

            AbstractCard tmp = card.makeSameInstanceOf();
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;
            tmp.freeToPlayOnce = true;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            lastCardQueueItem = new CardQueueItem(tmp, m, card.energyOnUse);
        }
    }

    @Override
    public void onInitialApplication() {
        if (AbstractDungeon.player.chosenClass == PlayerClassEnum.BLACKBEARD_CLASS) {
            Skeleton skeleton = (Skeleton) ReflectionHacks.getPrivate(AbstractDungeon.player, AbstractCreature.class, "skeleton");
            String attachName = "Parrot";
            String attachCloneName = "ParrotClone";
            int origSlotIndex = skeleton.findSlotIndex(attachName);

            // Create a new slot for the attachment
            Slot origSlot = skeleton.findSlot(attachName);
            Slot slotClone = new Slot(new SlotData(origSlot.getData().getIndex(), attachCloneName, origSlot.getBone().getData()), origSlot.getBone());
            slotClone.getData().setBlendMode(origSlot.getData().getBlendMode());
            skeleton.getSlots().insert(origSlotIndex, slotClone);

            // Add new slot to draw order
            Array<Slot> drawOrder = skeleton.getDrawOrder();
            drawOrder.add(slotClone);
            skeleton.setDrawOrder(drawOrder);

            Texture tex = ImageMaster.loadImage(BLACKBEARD_PARROT_PATH);
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            TextureRegion region = new TextureRegion(tex);
            RegionAttachment attachment = new RegionAttachment("ParrotClone");
            attachment.setRegion(region);
            attachment.setWidth(tex.getWidth());
            attachment.setHeight(tex.getHeight());
            attachment.setX(0 * Settings.scale);
            attachment.setY(0 * Settings.scale);
            attachment.setScaleX(1 * Settings.scale);
            attachment.setScaleY(1 * Settings.scale);
            attachment.setRotation(0.0f);
            attachment.updateOffset();

            Skin skin = skeleton.getData().getDefaultSkin();
            skin.addAttachment(origSlotIndex, attachment.getName(), attachment);

            skeleton.setAttachment(attachCloneName, attachment.getName());
        }
    }

    @Override
    public void onRemove() {

    }
}