package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.actions.EquipAction;
import blackbeard.enums.AbstractCardEnum;
import blackbeard.orbs.PowderKegOrb;
import blackbeard.utils.WeaponCardsUtil;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PowderKeg extends CustomCard {

    public static final String ID = "blackbeard:PowderKeg";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int WEAPON_ATTACK = 0;
    private static final int WEAPON_DURABILITY = 1;
    private static final int DAMAGE_ON_DESTROY = 15;
    private static final int UPGRADED_PLUS_DAMAGE_ON_DESTROY = 5;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = WeaponCardsUtil.getWeaponRawDescription(cardStrings.DESCRIPTION, WEAPON_ATTACK, WEAPON_DURABILITY);

    public PowderKeg() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(PowderKeg.ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = DAMAGE_ON_DESTROY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new EquipAction(new PowderKegOrb(WEAPON_ATTACK, WEAPON_DURABILITY, this.magicNumber, false)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_PLUS_DAMAGE_ON_DESTROY);
        }
    }
}
