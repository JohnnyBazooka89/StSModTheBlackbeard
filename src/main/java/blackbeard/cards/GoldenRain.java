package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.characters.TheBlackbeard;
import blackbeard.patches.AbstractCardEnum;
import blackbeard.utils.GoldenCardsUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GoldenRain extends CustomCard {
    public static final String ID = "blackbeard:GoldenRain";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int PERCENT_OF_GOLD_GAINED = 4;
    private static final int UPGRADE_PLUS_PERCENT_OF_GOLD_GAINED = 1;

    public GoldenRain() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(TheBlackbeard.DEFAULT_ATTACK_CARD_ID), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.RARE, CardTarget.ALL_ENEMY);

        this.baseDamage = 0;
        this.baseMagicNumber = PERCENT_OF_GOLD_GAINED;
        this.isMultiDamage = true;

        if (CardCrawlGame.isInARun()) {
            this.rawDescription = GoldenCardsUtil.getGoldenCardDescription(DESCRIPTION, EXTENDED_DESCRIPTION);
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void applyPowers() {
        this.baseDamage = (CardCrawlGame.goldGained / (100 / this.baseMagicNumber));
        super.applyPowers();
        this.rawDescription = GoldenCardsUtil.getGoldenCardDescription(DESCRIPTION, EXTENDED_DESCRIPTION) + EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        this.rawDescription = GoldenCardsUtil.getGoldenCardDescription(DESCRIPTION, EXTENDED_DESCRIPTION);
        this.initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_PERCENT_OF_GOLD_GAINED);
        }
    }
}
