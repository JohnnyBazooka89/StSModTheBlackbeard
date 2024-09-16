package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.EquipAction;
import blackbeard.enums.CardColorEnum;
import blackbeard.orbs.VampiricScepterOrb;
import blackbeard.utils.WeaponsUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackbeard.TheBlackbeardMod.makeID;

public class VampiricScepter extends AbstractBlackbeardCard {

    public static final String ID = makeID("VampiricScepter");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int WEAPON_ATTACK = 7;
    private static final int WEAPON_DURABILITY = 3;
    private static final int HEAL_VALUE = 2;
    private static final int UPGRADE_PLUS_WEAPON_ATTACK = 1;
    private static final int UPGRADE_PLUS_HEAL_VALUE = 1;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = WeaponsUtils.getWeaponRawDescription(cardStrings.DESCRIPTION, WEAPON_ATTACK, WEAPON_DURABILITY);

    public VampiricScepter() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(VampiricScepter.ID), COST, DESCRIPTION, CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.SPECIAL, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = WEAPON_ATTACK;
        this.baseSecondMagicNumber = this.secondMagicNumber = HEAL_VALUE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EquipAction(new VampiricScepterOrb(this.magicNumber, WEAPON_DURABILITY, this.secondMagicNumber, false)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_WEAPON_ATTACK);
            this.upgradeSecondMagic(UPGRADE_PLUS_HEAL_VALUE);
        }
    }

}
