package blackbeard.orbs;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.OrbStrings;

public class DefaultWeaponOrb extends WeaponOrb {

    public static final String ID = "blackbeard:DefaultWeaponOrb";
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;

    public DefaultWeaponOrb(int attack, int durability) {
        super(ID, NAME, DESCRIPTION[0], WeaponOrb.DEFAULT_IMAGE_PATH, attack, durability);
        this.attack = attack;
        this.durability = durability;
    }

}
