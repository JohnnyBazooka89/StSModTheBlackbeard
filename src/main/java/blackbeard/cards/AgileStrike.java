package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.enums.AbstractCardEnum;
import blackbeard.orbs.WeaponOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

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
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        this.baseDamage = this.damage = ATTACK_DMG;

        this.tags.add(CardTags.STRIKE);

        if (CardCrawlGame.isInARun() && (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)) {
            reduceCostToZeroForATurnIfNeeded();
        }
    }

    /* Cost Refreshing Logic is also in EquipAction. */

    @Override
    public void applyPowers() {
        super.applyPowers();
        reduceCostToZeroForATurnIfNeeded();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    private void reduceCostToZeroForATurnIfNeeded() {
        boolean shouldCostBeReduced = false;
        Iterator<AbstractOrb> orbsChanneledThisTurn = AbstractDungeon.actionManager.orbsChanneledThisTurn.iterator();

        while (orbsChanneledThisTurn.hasNext()) {
            AbstractOrb orb = orbsChanneledThisTurn.next();
            if (orb instanceof WeaponOrb) {
                shouldCostBeReduced = true;
            }
        }

        if (shouldCostBeReduced) {
            setCostForTurn(0);
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
