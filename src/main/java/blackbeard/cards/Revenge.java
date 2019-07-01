package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.enums.CardColorEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Revenge extends AbstractBlackbeardCard {
    public static final String ID = "blackbeard:Revenge";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int MAXIMUM_DAMAGE = 50;
    private static final int UPGRADE_PLUS_MAXIMUM_DAMAGE = 10;

    public Revenge() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        this.baseDamage = this.damage = 0;
        this.baseMagicNumber = this.magicNumber = MAXIMUM_DAMAGE;
        this.isMultiDamage = true;

        if (CardCrawlGame.isInARun()) {
            setBaseDamageAndUpdateDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void applyPowers() {
        setBaseDamageAndUpdateDescription();
        super.applyPowers();
    }

    /* Refreshing logic is in UpdateRevengePatch. */

    public void setBaseDamageAndUpdateDescription() {
        /*For example, when max HP = 75, then it deals 0% at 75HP, 50% at 38HP, and 100% at 1HP*/
        this.baseDamage = this.damage = (int) ((1.0 - (1.0 * (AbstractDungeon.player.currentHealth - 1) / (AbstractDungeon.player.maxHealth - 1))) * this.magicNumber);
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAXIMUM_DAMAGE);
            if (CardCrawlGame.isInARun()) {
                setBaseDamageAndUpdateDescription();
            }
        }
    }
}
