package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.EquipAction;
import blackbeard.enums.CardColorEnum;
import blackbeard.orbs.ReapersScytheOrb;
import blackbeard.utils.WeaponsUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackbeard.TheBlackbeardMod.makeID;

public class ReapersScythe extends AbstractBlackbeardCard {

    public static final String ID = makeID("ReapersScythe");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;
    private static final int WEAPON_ATTACK = 8;
    private static final int WEAPON_DURABILITY = 3;
    private static final int UPGRADE_PLUS_WEAPON_ATTACK = 2;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = WeaponsUtils.getWeaponRawDescription(cardStrings.DESCRIPTION, WEAPON_ATTACK, WEAPON_DURABILITY);

    public ReapersScythe() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ReapersScythe.ID), COST, DESCRIPTION, CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.RARE, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = WEAPON_ATTACK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EquipAction(new ReapersScytheOrb(this.magicNumber, WEAPON_DURABILITY, false)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_WEAPON_ATTACK);
        }
    }
}
