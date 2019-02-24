package blackbeard.orbs;

import blackbeard.TheBlackbeardMod;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

public class VampiricScepterOrb extends AbstractWeaponOrb {

    public static final String ID = "blackbeard:VampiricScepterOrb";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;

    private int healOnUse;

    public VampiricScepterOrb(int attack, int durability, int healOnUse, boolean justAddedUsingAttackCard) {
        super(ID, NAME, DESCRIPTION[0], TheBlackbeardMod.getOrbImagePath(ID), attack, durability, justAddedUsingAttackCard);
        this.attack = attack;
        this.durability = durability;
        this.healOnUse = healOnUse;
        this.rawDescription = DESCRIPTION[0] + healOnUse + DESCRIPTION[1];
        this.updateDescription();
    }

    @Override
    public void effectOnUse() {
        AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.healOnUse));
    }

    @Override
    public AbstractWeaponOrb makeCopy() {
        return new PowderKegOrb(attack, durability, healOnUse, justAddedUsingAttackCard);
    }
}
