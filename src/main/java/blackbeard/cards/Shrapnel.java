package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.enums.CardColorEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;

import static blackbeard.TheBlackbeardMod.makeID;

public class Shrapnel extends AbstractBlackbeardCard {
    public static final String ID = makeID("Shrapnel");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 7;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 3;

    public Shrapnel() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);

        this.baseDamage = this.damage = ATTACK_DMG;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }
}
