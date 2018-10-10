package blackbeard.orbs;

import blackbeard.TheBlackbeardMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class CatONineTailsOrb extends WeaponOrb {

    public static final String ID = "blackbeard:CatONineTailsOrb";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;

    public CatONineTailsOrb(int attack, int durability) {
        super(ID, NAME, DESCRIPTION[0], TheBlackbeardMod.getOrbImagePath(ID), attack, durability);
        this.attack = attack;
        this.durability = durability;
    }

    @Override
    public void effectOnUse(AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, 1, false), 1));
    }
}
