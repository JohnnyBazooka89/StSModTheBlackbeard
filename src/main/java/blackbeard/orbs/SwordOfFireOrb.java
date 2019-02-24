package blackbeard.orbs;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.DamageAllEnemiesWithDamageMatrixAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

public class SwordOfFireOrb extends AbstractWeaponOrb {

    public static final String ID = "blackbeard:SwordOfFireOrb";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;

    public SwordOfFireOrb(int attack, int durability, boolean justAddedUsingAttackCard) {
        super(ID, NAME, DESCRIPTION[0], TheBlackbeardMod.getOrbImagePath(ID), attack, durability, justAddedUsingAttackCard);
        this.attack = attack;
        this.durability = durability;
    }

    @Override
    public void effectOnUse() {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesWithDamageMatrixAction(3));
    }
}
