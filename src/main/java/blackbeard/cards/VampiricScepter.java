package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.actions.EquipAction;
import blackbeard.enums.AbstractCardEnum;
import blackbeard.interfaces.ISecondMagicNumber;
import blackbeard.orbs.VampiricScepterOrb;
import blackbeard.utils.WeaponCardsUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VampiricScepter extends CustomCard implements ISecondMagicNumber {

    public static final String ID = "blackbeard:VampiricScepter";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int WEAPON_ATTACK = 7;
    private static final int WEAPON_DURABILITY = 3;
    private static final int HEAL_VALUE = 3;
    private static final int UPGRADED_PLUS_WEAPON_ATTACK = 1;
    private static final int UPGRADED_PLUS_HEAL_VALUE = 1;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = WeaponCardsUtils.getWeaponRawDescription(cardStrings.DESCRIPTION, WEAPON_ATTACK, WEAPON_DURABILITY);

    private int customHeal;

    public VampiricScepter() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(VampiricScepter.ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.SPECIAL, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = WEAPON_ATTACK;
        this.customHeal = HEAL_VALUE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new EquipAction(new VampiricScepterOrb(this.magicNumber, WEAPON_DURABILITY, this.customHeal, false)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_PLUS_WEAPON_ATTACK);
            this.customHeal += UPGRADED_PLUS_HEAL_VALUE;
        }
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        return this.customHeal;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return this.customHeal;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return upgraded;
    }
}
