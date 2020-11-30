package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.DestroyWeaponsAction;
import blackbeard.enums.CardColorEnum;
import blackbeard.enums.WeaponsToUseEnum;
import blackbeard.powers.WeaponPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Scrap extends AbstractBlackbeardCard {

    public static final String ID = "blackbeard:Scrap";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 0;
    private static final int ENERGY_TO_GET = 2;
    private static final int UPGRADE_PLUS_ENERGY_TO_GET = 1;

    public Scrap() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = ENERGY_TO_GET;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DestroyWeaponsAction(WeaponsToUseEnum.ONLY_RIGHTMOST_WEAPON));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = false;
        if (AbstractDungeon.player.hasPower(WeaponPower.POWER_ID)) {
            WeaponPower weaponPower = (WeaponPower) AbstractDungeon.player.getPower(WeaponPower.POWER_ID);
            if (weaponPower.getNumberOfWeapons() >= 1) {
                canUse = true;
            }
        }

        if (!canUse) {
            this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        }
        return canUse;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_ENERGY_TO_GET);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
