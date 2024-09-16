package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.enums.CardColorEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackbeard.TheBlackbeardMod.makeID;

public class BlindAttacks extends AbstractBlackbeardCard {
    public static final String ID = makeID("BlindAttacks");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 3;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 1;
    private static final int NUMBER_OF_ATTACKS = 3;

    public BlindAttacks() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);

        this.baseDamage = this.damage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < NUMBER_OF_ATTACKS; i++) {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }
}
