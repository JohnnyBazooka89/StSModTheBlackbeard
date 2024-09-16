package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.ShootAnythingAction;
import blackbeard.enums.CardColorEnum;
import blackbeard.enums.CardTagsEnum;
import blackbeard.patches.CannonballDamageInfoPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackbeard.TheBlackbeardMod.makeID;

public class DoubleCannonball extends AbstractBlackbeardCard {
    public static final String ID = makeID("DoubleCannonball");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 6;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 2;

    public DoubleCannonball() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        this.baseDamage = this.damage = ATTACK_DMG;
        this.exhaust = true;

        this.tags.add(CardTagsEnum.CANNONBALL);
        this.cardsToPreview = new Cannonball();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ShootAnythingAction(m, getCannonballTexture(), false));
        DamageInfo damageInfo = new DamageInfo(p, this.damage, this.damageTypeForTurn);
        CannonballDamageInfoPatch.cannonballDamageInfo.set(damageInfo, true);
        addToBot(new DamageAction(m, damageInfo, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractCard cannonball = new Cannonball();
        if (this.upgraded) {
            cannonball.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(cannonball, 1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
            this.cardsToPreview.upgrade();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
