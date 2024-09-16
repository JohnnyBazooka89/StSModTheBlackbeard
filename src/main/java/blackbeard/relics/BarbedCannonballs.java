package blackbeard.relics;

import blackbeard.TheBlackbeardMod;
import blackbeard.patches.CannonballDamageInfoPatch;
import blackbeard.powers.BleedPower;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static blackbeard.TheBlackbeardMod.makeID;

public class BarbedCannonballs extends AbstractBlackbeardRelic {

    public static final String ID = makeID("BarbedCannonballs");
    private static final Texture IMG = TextureLoader.getTexture(TheBlackbeardMod.getRelicImagePath(ID));
    private static final Texture OUTLINE = TextureLoader.getTexture(TheBlackbeardMod.getRelicOutlineImagePath(ID));

    private static final int BLEED_TO_APPLY = 1;

    public BarbedCannonballs() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (CannonballDamageInfoPatch.cannonballDamageInfo.get(info) && damageAmount > 0 && target != AbstractDungeon.player) {
            flash();
            addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new BleedPower(target, BLEED_TO_APPLY), BLEED_TO_APPLY, true));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLEED_TO_APPLY + DESCRIPTIONS[1];
    }
}
