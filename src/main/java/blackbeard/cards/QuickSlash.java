package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.actions.EquipAction;
import blackbeard.characters.TheBlackbeard;
import blackbeard.orbs.DefaultWeaponOrb;
import blackbeard.patches.AbstractCardEnum;
import blackbeard.utils.WeaponCardsUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QuickSlash extends CustomCard {
    public static final String ID = "blackbeard:QuickSlash";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DAMAGE = 3;
    private static final int WEAPON_ATTACK = 3;
    private static final int WEAPON_DURABILITY = 2;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = WeaponCardsUtil.getWeaponRawDescription(cardStrings.DESCRIPTION, WEAPON_ATTACK, WEAPON_DURABILITY);

    public QuickSlash() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(TheBlackbeard.DEFAULT_ATTACK_CARD_ID), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);

        this.baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractDungeon.actionManager.addToBottom(new EquipAction(new DefaultWeaponOrb(WEAPON_ATTACK, WEAPON_DURABILITY)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DAMAGE);
        }
    }

}
