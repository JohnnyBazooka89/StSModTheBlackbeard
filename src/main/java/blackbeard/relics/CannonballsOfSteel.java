package blackbeard.relics;

import blackbeard.TheBlackbeardMod;
import blackbeard.enums.CardTagsEnum;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static blackbeard.TheBlackbeardMod.makeID;

public class CannonballsOfSteel extends AbstractBlackbeardRelic {

    public static final String ID = makeID("CannonballsOfSteel");
    private static final Texture IMG = TextureLoader.getTexture(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = TextureLoader.getTexture(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    public CannonballsOfSteel() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        return c.hasTag(CardTagsEnum.CANNONBALL) ? damage + 2.0F : damage;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
