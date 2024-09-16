package blackbeard.orbs;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.DamageAllEnemiesWithDamageMatrixAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

import static blackbeard.TheBlackbeardMod.makeID;

public class PowderKegOrb extends AbstractWeaponOrb {

    public static final String ID = makeID("PowderKegOrb");
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;

    private int damageOnDestroy;

    public PowderKegOrb(int attack, int durability, int damageOnDestroy, boolean justAddedUsingAttackCard) {
        super(ID, NAME, DESCRIPTION[0], TheBlackbeardMod.getOrbImagePath(ID), attack, durability, justAddedUsingAttackCard);
        this.attack = attack;
        this.durability = durability;
        this.damageOnDestroy = damageOnDestroy;
        this.rawDescription = DESCRIPTION[0] + damageOnDestroy + DESCRIPTION[1];
        this.updateDescription();
    }

    @Override
    public void onEvoke() {
        super.onEvoke();
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesWithDamageMatrixAction(this.damageOnDestroy));
    }

    @Override
    public AbstractWeaponOrb makeCopy() {
        return new PowderKegOrb(attack, durability, damageOnDestroy, justAddedUsingAttackCard);
    }
}
