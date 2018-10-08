package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.actions.UpgradeWeaponsAction;
import blackbeard.characters.TheBlackbeard;
import blackbeard.enums.WeaponsToUseEnum;
import blackbeard.patches.AbstractCardEnum;
import blackbeard.utils.WeaponCardsUtil;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fencing extends CustomCard {

    public static final String ID = "blackbeard:Fencing";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADE_VALUE = 2;
    private static final int UPGRADED_PLUS_VALUE = 1;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = WeaponCardsUtil.getWeaponRawDescription(cardStrings.DESCRIPTION, UPGRADE_VALUE, UPGRADE_VALUE);

    public Fencing() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(TheBlackbeard.DEFAULT_SKILL_CARD_ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.COMMON, CardTarget.SELF);

        this.baseMagicNumber = UPGRADE_VALUE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new UpgradeWeaponsAction(this.baseMagicNumber, this.baseMagicNumber, WeaponsToUseEnum.ONLY_RIGHTMOST_WEAPON));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = "test";
            this.upgradeMagicNumber(UPGRADED_PLUS_VALUE);
        }
    }
}
