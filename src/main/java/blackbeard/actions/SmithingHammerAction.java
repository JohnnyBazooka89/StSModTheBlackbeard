package blackbeard.actions;

import blackbeard.orbs.SmithingHammerOrb;
import blackbeard.powers.WeaponPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SmithingHammerAction extends AbstractGameAction {

    private AbstractPlayer p;
    private SmithingHammerOrb smithingHammerOrb;
    private boolean playedAutomatically;

    public SmithingHammerAction(SmithingHammerOrb smithingHammerOrb, boolean playedAutomatically) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.smithingHammerOrb = smithingHammerOrb;
        this.duration = Settings.ACTION_DUR_FAST;
        this.playedAutomatically = playedAutomatically;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.group.isEmpty() && p.drawPile.group.isEmpty()) {
                this.isDone = true;
            } else {
                CardGroup handUpgradeable = getUpgradeableCardsInCardGroup(this.p.hand);
                CardGroup drawPileUpgradeable = getUpgradeableCardsInCardGroup(this.p.drawPile);

                boolean cardInHandUpgraded = false;
                boolean cardInDrawPileUpgraded = false;

                if (handUpgradeable.size() > 0) {
                    upgradeRandomCardInCardGroup(handUpgradeable);
                    cardInHandUpgraded = true;
                }

                if (drawPileUpgradeable.size() > 0) {
                    upgradeRandomCardInCardGroup(drawPileUpgradeable);
                    cardInDrawPileUpgraded = true;
                }

                if (playedAutomatically && (cardInHandUpgraded || cardInDrawPileUpgraded)) {
                    smithingHammerOrb.use(false);
                    if (AbstractDungeon.player.hasPower(WeaponPower.POWER_ID)) {
                        WeaponPower weaponPower = (WeaponPower) AbstractDungeon.player.getPower(WeaponPower.POWER_ID);
                        weaponPower.refreshWeapons();
                    }
                }

                this.isDone = true;
            }
        } else {
            this.tickDuration();
        }
    }

    private static void upgradeRandomCardInCardGroup(CardGroup cardGroup) {
        cardGroup.shuffle();
        cardGroup.group.get(0).upgrade();
        cardGroup.group.get(0).superFlash();
        cardGroup.group.get(0).applyPowers();
    }

    public CardGroup getUpgradeableCardsInCardGroup(CardGroup cardGroup) {
        CardGroup upgradeable = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : cardGroup.group) {
            if (c.canUpgrade() && c.type != AbstractCard.CardType.STATUS) {
                upgradeable.addToTop(c);
            }
        }

        return upgradeable;
    }

}
