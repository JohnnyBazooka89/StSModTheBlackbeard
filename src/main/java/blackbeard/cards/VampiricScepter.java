package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.EquipAction;
import blackbeard.enums.CardColorEnum;
import blackbeard.interfaces.ICustomHealMagicNumber;
import blackbeard.orbs.VampiricScepterOrb;
import blackbeard.utils.WeaponCardsUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VampiricScepter extends AbstractBlackbeardCard implements ICustomHealMagicNumber {

    public static final String ID = "blackbeard:VampiricScepter";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int WEAPON_ATTACK = 7;
    private static final int WEAPON_DURABILITY = 3;
    private static final int HEAL_VALUE = 2;
    private static final int UPGRADE_PLUS_WEAPON_ATTACK = 1;
    private static final int UPGRADE_PLUS_HEAL_VALUE = 1;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = WeaponCardsUtils.getWeaponRawDescription(cardStrings.DESCRIPTION, WEAPON_ATTACK, WEAPON_DURABILITY);

    private int customHeal;
    private boolean customHealDisplayUpgradesModified;

    public VampiricScepter() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(VampiricScepter.ID), COST, DESCRIPTION, CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.SPECIAL, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = WEAPON_ATTACK;
        this.customHeal = HEAL_VALUE;
        this.customHealDisplayUpgradesModified = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EquipAction(new VampiricScepterOrb(this.magicNumber, WEAPON_DURABILITY, this.customHeal, false)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_WEAPON_ATTACK);
            this.customHeal += UPGRADE_PLUS_HEAL_VALUE;
        }
    }

    @Override
    public boolean isModified() {
        return this.customHealDisplayUpgradesModified;
    }

    @Override
    public int value() {
        return this.customHeal;
    }

    @Override
    public int baseValue() {
        return this.customHeal;
    }

    @Override
    public boolean upgraded() {
        return upgraded;
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        if (this.upgraded) {
            customHealDisplayUpgradesModified = true;
        }
    }
}
