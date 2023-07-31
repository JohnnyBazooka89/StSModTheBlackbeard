package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.enums.CardColorEnum;
import blackbeard.interfaces.IGoldenCard;
import blackbeard.utils.GoldenCardsUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;

public class GoldenNoose extends AbstractBlackbeardCard implements IGoldenCard {
    public static final String ID = "blackbeard:GoldenNoose";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 3;
    private static final int PERCENT_OF_GOLD_GAINED = 4;
    private static final int UPGRADE_PLUS_PERCENT_OF_GOLD_GAINED = 1;

    public GoldenNoose() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.RARE, CardTarget.ENEMY);

        this.baseMagicNumber = this.magicNumber = PERCENT_OF_GOLD_GAINED;

        setGoldenValuesAndUpdateDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, false)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void applyPowers() {
        setGoldenValuesAndUpdateDescription();
        super.applyPowers();
    }

    @Override
    public void setGoldenValuesAndUpdateDescription() {
        this.baseDamage = this.damage = this.magicNumber * GoldenCardsUtils.getBlackbeardGoldGained() / 100;
        this.rawDescription = GoldenCardsUtils.getGoldenCardDescription(this.upgraded, DESCRIPTION, UPGRADE_DESCRIPTION, EXTENDED_DESCRIPTION);
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_PERCENT_OF_GOLD_GAINED);
            setGoldenValuesAndUpdateDescription();
        }
    }

}
