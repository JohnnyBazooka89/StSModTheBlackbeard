package blackbeard.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TheDrunkenSailorPower extends AbstractPower {
    public static final String POWER_ID = "blackbeard:TheDrunkenSailorPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TheDrunkenSailorPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        //this.loadRegion("regen");
        this.region48 = new TextureAtlas.AtlasRegion(new Texture("img/powers/WeaponPower2.png"), 0, 0, 96, 96);
        //this.region128 = new TextureAtlas.AtlasRegion(new Texture("img/powers/WeaponPower2.png"), 0, 0, 96, 96);

        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (this.amount + 1) + DESCRIPTIONS[1];
    }

    //Logic is in TheDrunkenSailorPatch.
}