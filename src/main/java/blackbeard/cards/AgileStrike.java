package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.characters.TheBlackbeard;
import blackbeard.orbs.WeaponOrb;
import blackbeard.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.Iterator;

public class AgileStrike extends CustomCard {
    public static final String ID = "blackbeard:AgileStrike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 9;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 4;

    public AgileStrike() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(TheBlackbeard.DEFAULT_ATTACK_CARD_ID), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        this.baseDamage = ATTACK_DMG;

        this.tags.add(CardTags.STRIKE);

        reduceCostToZeroIfEquippedWeaponThisTurn();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        reduceCostToZeroIfEquippedWeaponThisTurn();
    }

    private void reduceCostToZeroIfEquippedWeaponThisTurn() {
        boolean shouldReduceCostToZero = false;
        Iterator<AbstractOrb> orbsChanneledThisTurnIt = AbstractDungeon.actionManager.orbsChanneledThisTurn.iterator();
        while (orbsChanneledThisTurnIt.hasNext()) {
            AbstractOrb orb = orbsChanneledThisTurnIt.next();
            if (orb instanceof WeaponOrb) {
                shouldReduceCostToZero = true;
            }
        }

        if (shouldReduceCostToZero) {
            this.setCostForTurn(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }
}
