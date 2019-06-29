package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.actions.EquipAction;
import blackbeard.enums.CardColorEnum;
import blackbeard.orbs.SwordOfFireOrb;
import blackbeard.utils.WeaponCardsUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SwordOfFire extends CustomCard {

    public static final String ID = "blackbeard:SwordOfFire";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 0;
    private static final int WEAPON_ATTACK = 3;
    private static final int WEAPON_DURABILITY = 3;
    private static final int UPGRADED_PLUS_WEAPON_DURABILITY = 1;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = WeaponCardsUtils.getWeaponRawDescription(cardStrings.DESCRIPTION, WEAPON_ATTACK, WEAPON_DURABILITY);

    public SwordOfFire() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.SPECIAL, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = WEAPON_DURABILITY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new EquipAction(new SwordOfFireOrb(WEAPON_ATTACK, this.magicNumber, false)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_PLUS_WEAPON_DURABILITY);
        }
    }
}
