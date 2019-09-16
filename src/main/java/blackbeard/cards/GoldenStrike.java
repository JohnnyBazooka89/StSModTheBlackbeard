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

public class GoldenStrike extends AbstractBlackbeardCard implements IGoldenCard {
    public static final String ID = "blackbeard:GoldenStrike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 0;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 3;

    public GoldenStrike() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.COMMON, CardTarget.ENEMY);

        this.baseMagicNumber = this.magicNumber = ATTACK_DMG;

        this.tags.add(CardTags.STRIKE);

        setGoldenValuesAndUpdateDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, false)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void applyPowers() {
        setGoldenValuesAndUpdateDescription();
        super.applyPowers();
    }

    @Override
    public void setGoldenValuesAndUpdateDescription() {
        this.baseDamage = this.damage = this.magicNumber + (3 * GoldenCardsUtils.getBlackbeardGoldGained() / 200);
        this.rawDescription = GoldenCardsUtils.getGoldenCardDescription(upgraded, DESCRIPTION, UPGRADE_DESCRIPTION, EXTENDED_DESCRIPTION);
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_ATTACK_DMG);
            setGoldenValuesAndUpdateDescription();
        }
    }

}
