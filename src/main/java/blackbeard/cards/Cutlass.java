package blackbeard.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import blackbeard.TheBlackbeardMod;
import blackbeard.actions.EquipAction;
import blackbeard.orbs.CutlassOrb;
import blackbeard.patches.AbstractCardEnum;
import blackbeard.utils.WeaponCardsUtil;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Cutlass extends CustomCard {

    public static final String ID = "blackbeard:Cutlass";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private static final int WEAPON_ATTACK = 3;
    private static final int WEAPON_DURABILITY = 2;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = WeaponCardsUtil.getWeaponRawDescription(cardStrings.DESCRIPTION, WEAPON_ATTACK, WEAPON_DURABILITY);

    public Cutlass() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(Cutlass.ID), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.BASIC, AbstractCard.CardTarget.SELF);

        this.tags.add(BaseModCardTags.GREMLIN_MATCH);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new EquipAction(new CutlassOrb(WEAPON_ATTACK, WEAPON_DURABILITY)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
        }
    }
}
