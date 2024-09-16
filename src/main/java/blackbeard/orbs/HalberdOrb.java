package blackbeard.orbs;

import blackbeard.TheBlackbeardMod;
import blackbeard.powers.BleedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

import static blackbeard.TheBlackbeardMod.makeID;

public class HalberdOrb extends AbstractWeaponOrb {

    public static final String ID = makeID("HalberdOrb");
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;

    public HalberdOrb(int attack, int durability, boolean justAddedUsingAttackCard) {
        super(ID, NAME, DESCRIPTION[0], TheBlackbeardMod.getOrbImagePath(ID), attack, durability, justAddedUsingAttackCard);
        this.attack = attack;
        this.durability = durability;
    }

    @Override
    public void effectOnAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new BleedPower(target, 3), 3));
    }
}
