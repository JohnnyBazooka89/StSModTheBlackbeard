package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import blackbeard.TheBlackbeardMod;
import blackbeard.actions.EquipAction;
import blackbeard.orbs.PowderKegOrb;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PowderCan extends CustomRelic {
    public static final String ID = "blackbeard:PowderCan";

    private static final int WEAPON_ATTACK = 0;
    private static final int WEAPON_DURABILITY = 1;
    private static final int DAMAGE_ON_DESTROY = 8;

    public PowderCan() {
        super(ID, new Texture(TheBlackbeardMod.getRelicImagePath(ID)), new Texture(TheBlackbeardMod.getRelicOutlineImagePath(ID)), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new EquipAction(new PowderKegOrb(WEAPON_ATTACK, WEAPON_DURABILITY, DAMAGE_ON_DESTROY, false)));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
