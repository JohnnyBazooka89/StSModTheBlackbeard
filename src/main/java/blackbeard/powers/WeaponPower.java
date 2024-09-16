package blackbeard.powers;

import blackbeard.orbs.AbstractWeaponOrb;
import blackbeard.relics.Karategi;
import blackbeard.relics.Penknife;
import blackbeard.relics.Spearhead;
import blackbeard.utils.WeaponsUtils;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;

import static blackbeard.TheBlackbeardMod.makeID;

public class WeaponPower extends AbstractPower implements InvisiblePower {

    public static final String POWER_ID = makeID("WeaponPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;

    public WeaponPower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = -1;
        this.type = NeutralPowertypePatch.NEUTRAL;
        this.loadRegion("painfulStabs");
        this.updateDescription();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type.equals(CardType.ATTACK)) {
            for (AbstractWeaponOrb weaponOrb : getWeaponsToUse()) {
                if (!weaponOrb.isJustAddedUsingAttackCard()) {
                    weaponOrb.use(true);
                }
            }
            clearJustAddedUsingAttackCardFlagInWeapons();
            if (AbstractDungeon.player.hasRelic(Karategi.ID)) {
                List<AbstractWeaponOrb> weaponsToUse = getWeaponsToUse();
                AbstractDungeon.player.orbs.removeAll(weaponsToUse);
                AbstractDungeon.player.orbs.addAll(weaponsToUse);
            }
        }
        refreshWeapons();
    }

    public void refreshWeapons() {
        destroyWeaponsWithNoDurability();
        setRelicPulsing();
        AbstractDungeon.player.hand.applyPowers();
    }

    private void destroyWeaponsWithNoDurability() {
        List<AbstractOrb> weaponsToRemove = new ArrayList<>();

        boolean hasAtLeastOneWeaponLeft = false;

        List<AbstractOrb> orbsList = AbstractDungeon.player.orbs;
        for (int i = 0; i < orbsList.size(); i++) {
            AbstractOrb orb = orbsList.get(i);
            if (orb instanceof AbstractWeaponOrb) {
                AbstractWeaponOrb weaponOrb = (AbstractWeaponOrb) orb;
                if (weaponOrb.getDurability() <= 0) {
                    weaponsToRemove.add(orb);
                } else {
                    hasAtLeastOneWeaponLeft = true;
                }
            }
        }

        for (int i = 0; i < weaponsToRemove.size(); i++) {
            AbstractWeaponOrb weaponOrb = (AbstractWeaponOrb) weaponsToRemove.get(i);
            weaponOrb.onEvoke();
        }
        orbsList.removeAll(weaponsToRemove);

        AbstractDungeon.player.maxOrbs -= weaponsToRemove.size();
        if (AbstractDungeon.player.maxOrbs < 0) {
            AbstractDungeon.player.maxOrbs = 0;
        }

        for (int i = 0; i < orbsList.size(); ++i) {
            orbsList.get(i).setSlot(i, AbstractDungeon.player.maxOrbs);
        }

        if (!hasAtLeastOneWeaponLeft) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    private void clearJustAddedUsingAttackCardFlagInWeapons() {
        List<AbstractOrb> orbsList = AbstractDungeon.player.orbs;
        for (int i = 0; i < orbsList.size(); i++) {
            AbstractOrb orb = orbsList.get(i);
            if (orb instanceof AbstractWeaponOrb) {
                AbstractWeaponOrb weaponOrb = (AbstractWeaponOrb) orb;
                weaponOrb.clearJustAddedUsingAttackCard();
            }
        }
    }

    private void setRelicPulsing() {
        if (AbstractDungeon.player.hasRelic(Spearhead.ID)) {
            Spearhead spearhead = (Spearhead) AbstractDungeon.player.getRelic(Spearhead.ID);
            boolean shouldPulse = false;
            for (AbstractWeaponOrb weaponOrb : getWeaponsToUse()) {
                if (weaponOrb.getDurabilityForCombat() == 1) {
                    shouldPulse = true;
                }
            }
            if (shouldPulse) {
                spearhead.beginLongPulse();
            } else {
                spearhead.stopPulse();
            }
        }
        if (AbstractDungeon.player.hasRelic(Penknife.ID)) {
            Penknife penknife = (Penknife) AbstractDungeon.player.getRelic(Penknife.ID);
            boolean shouldPulse = WeaponsUtils.getNumberOfWeapons() > 0 && (penknife.counter == Penknife.COUNT - 1);
            if (shouldPulse) {
                penknife.beginLongPulse();
            } else {
                penknife.stopPulse();
            }
        }
    }



    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof AbstractWeaponOrb) {
                AbstractWeaponOrb weaponOrb = (AbstractWeaponOrb) o;
                weaponOrb.effectAtStartOfTurnPostDraw();
            }
        }
    }

    @Override
    public float atDamageGive(float damage, DamageType type) {
        if (type.equals(DamageType.NORMAL)) {
            int weaponAttack = 0;
            for (AbstractWeaponOrb weaponOrb : getWeaponsToUse()) {
                weaponAttack += weaponOrb.getDamageToDeal();
            }
            return damage + weaponAttack;
        } else {
            return damage;
        }
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        if (target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            for (AbstractWeaponOrb weaponOrb : getWeaponsToUse()) {
                weaponOrb.effectOnAttack(info, damageAmount, target);
            }
        }
    }

    private List<AbstractWeaponOrb> getWeaponsToUse() {
        int numberOfWeaponsToUse = 1;
        if (AbstractDungeon.player.hasPower(WeaponProficiencyPower.POWER_ID)) {
            WeaponProficiencyPower weaponProficiencyPower = (WeaponProficiencyPower) AbstractDungeon.player.getPower(WeaponProficiencyPower.POWER_ID);
            numberOfWeaponsToUse += weaponProficiencyPower.amount;
        }
        if (AbstractDungeon.player.hasPower(SwordDancePower.POWER_ID)) {
            numberOfWeaponsToUse = Integer.MAX_VALUE;
        }

        List<AbstractWeaponOrb> weaponsToUseResult = new ArrayList<>();
        int counter = 0;
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof AbstractWeaponOrb) {
                AbstractWeaponOrb weaponOrb = (AbstractWeaponOrb) o;
                weaponsToUseResult.add(weaponOrb);
                counter++;
                if (counter == numberOfWeaponsToUse) {
                    break;
                }
            }
        }
        return weaponsToUseResult;
    }
}
