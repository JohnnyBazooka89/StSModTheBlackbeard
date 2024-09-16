package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.EquipAction;
import blackbeard.enums.CardColorEnum;
import blackbeard.orbs.SpearOrb;
import blackbeard.utils.WeaponsUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackbeard.TheBlackbeardMod.makeID;

public class Spear extends AbstractBlackbeardCard {

    public static final String ID = makeID("Spear");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 0;
    private static final int WEAPON_ATTACK = 7;
    private static final int WEAPON_DURABILITY = 1;
    private static final int UPGRADE_PLUS_WEAPON_ATTACK = 3;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = WeaponsUtils.getWeaponRawDescription(cardStrings.DESCRIPTION, WEAPON_ATTACK, WEAPON_DURABILITY);

    public Spear() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.COMMON, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = WEAPON_ATTACK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EquipAction(new SpearOrb(this.magicNumber, WEAPON_DURABILITY, false)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_WEAPON_ATTACK);
        }
    }
}
