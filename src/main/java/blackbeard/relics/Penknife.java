package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import blackbeard.powers.PenknifePower;
import blackbeard.powers.WeaponPower;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class Penknife extends CustomRelic {

    public static final String ID = "blackbeard:Penknife";
    public static final Texture IMG = ImageMaster.loadImage(TheBlackbeardMod.getRelicImagePath(ID));
    public static final Texture OUTLINE = ImageMaster.loadImage(TheBlackbeardMod.getRelicOutlineImagePath(ID));
    public static final int COUNT = 8;

    public Penknife() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && AbstractDungeon.player.hasPower(WeaponPower.POWER_ID)) {
            ++this.counter;
            if (this.counter == COUNT) {
                this.counter = 0;
                this.flash();
            } else if (this.counter == COUNT - 1) {
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PenknifePower(AbstractDungeon.player, 1), 1, true));
            }
        }
    }

    @Override
    public void atBattleStart() {
        if (this.counter == COUNT - 1) {
            this.beginPulse();
            this.pulse = true;
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PenknifePower(AbstractDungeon.player, 1), 1, true));
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
