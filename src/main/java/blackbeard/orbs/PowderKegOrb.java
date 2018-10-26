package blackbeard.orbs;

import blackbeard.TheBlackbeardMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class PowderKegOrb extends WeaponOrb {

    public static final String ID = "blackbeard:PowderKegOrb";
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
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(this.damageOnDestroy, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public AbstractOrb makeCopy() {
        return new PowderKegOrb(attack, durability, damageOnDestroy, justAddedUsingAttackCard);
    }
}
