package blackbeard.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import blackbeard.TheBlackbeardMod;
import blackbeard.actions.SwordDiscoveryAction;
import blackbeard.enums.AbstractCardEnum;
import blackbeard.patches.SwordDiscoveryPatch;
import blackbeard.utils.WeaponCardsUtil;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

public class Sword extends CustomCard {

    public static final String ID = "blackbeard:Sword";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int WEAPON_ATTACK = 2;
    private static final int WEAPON_DURABILITY = 3;
    private static final int UPGRADED_PLUS_WEAPON_ATTACK = 1;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = WeaponCardsUtil.getWeaponRawDescription(cardStrings.DESCRIPTION, WEAPON_ATTACK, WEAPON_DURABILITY);
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public Sword() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.COMMON, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = WEAPON_ATTACK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        SwordDiscoveryPatch.swordDiscovery = true;
        SwordDiscoveryPatch.upgraded = this.upgraded;
        AbstractDungeon.actionManager.addToBottom(new SwordDiscoveryAction());
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_PLUS_WEAPON_ATTACK);
        }
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.clear();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tooltips = new ArrayList<>();
        tooltips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]));
        return tooltips;
    }

}
