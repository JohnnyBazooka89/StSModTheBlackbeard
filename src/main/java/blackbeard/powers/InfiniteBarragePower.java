package blackbeard.powers;

import blackbeard.TheBlackbeardMod;
import blackbeard.cards.Cannonball;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class InfiniteBarragePower extends AbstractPower {
    public static final String POWER_ID = "blackbeard:InfiniteBarragePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture power128Image = TextureLoader.getTexture(TheBlackbeardMod.getPower128ImagePath(POWER_ID));
    private static final Texture power48Image = TextureLoader.getTexture(TheBlackbeardMod.getPower48ImagePath(POWER_ID));

    public InfiniteBarragePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(power128Image, 0, 0, power128Image.getWidth(), power128Image.getHeight());
        this.region48 = new TextureAtlas.AtlasRegion(power48Image, 0, 0, power48Image.getWidth(), power48Image.getHeight());
        this.type = PowerType.BUFF;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        }
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Cannonball(), this.amount, false));
        }
    }
}