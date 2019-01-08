package blackbeard.powers;

import blackbeard.orbs.WeaponOrb;
import blackbeard.relics.Spearhead;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;

public class WeaponPower extends AbstractPower {

    public static final String POWER_ID = "blackbeard:WeaponPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public WeaponPower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = -1;
        this.loadRegion("painfulStabs");
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type.equals(CardType.ATTACK)) {
            for (WeaponOrb weaponOrb : getWeaponsToUse()) {
                if (!weaponOrb.isJustAddedUsingAttackCard()) {
                    weaponOrb.use(true);
                }
            }
            clearJustAddedUsingAttackCardFlagInWeapons();
        }
        refreshWeapons();
    }

    public void refreshWeapons() {
        destroyWeaponsWithNoDurability();
        setSpearheadPulsing();
    }

    private void destroyWeaponsWithNoDurability() {
        List<AbstractOrb> weaponsToRemove = new ArrayList<>();

        boolean hasAtLeastOneWeaponLeft = false;

        List<AbstractOrb> orbsList = AbstractDungeon.player.orbs;
        for (int i = 0; i < orbsList.size(); i++) {
            AbstractOrb orb = orbsList.get(i);
            if (orb instanceof WeaponOrb) {
                WeaponOrb weaponOrb = (WeaponOrb) orb;
                if (weaponOrb.getDurability() <= 0) {
                    weaponsToRemove.add(orb);
                } else {
                    hasAtLeastOneWeaponLeft = true;
                }
            }
        }

        for (int i = 0; i < weaponsToRemove.size(); i++) {
            WeaponOrb weaponOrb = (WeaponOrb) weaponsToRemove.get(i);
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
            if (orb instanceof WeaponOrb) {
                WeaponOrb weaponOrb = (WeaponOrb) orb;
                weaponOrb.clearJustAddedUsingAttackCard();
            }
        }
    }

    private void setSpearheadPulsing() {
        if (AbstractDungeon.player.hasRelic(Spearhead.ID)) {
            Spearhead spearhead = (Spearhead) AbstractDungeon.player.getRelic(Spearhead.ID);
            spearhead.stopPulse();
            boolean shouldPulse = false;
            for (WeaponOrb weaponOrb : getWeaponsToUse()) {
                if (weaponOrb.getDurability() == 1) {
                    shouldPulse = true;
                }
            }
            if (shouldPulse) {
                spearhead.beginLongPulse();
            }
        }
    }

    public int getNumberOfWeapons() {
        int result = 0;
        List<AbstractOrb> orbsList = AbstractDungeon.player.orbs;
        for (int i = 0; i < orbsList.size(); i++) {
            AbstractOrb orb = orbsList.get(i);
            if (orb instanceof WeaponOrb) {
                result++;
            }
        }
        return result;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof WeaponOrb) {
                WeaponOrb weaponOrb = (WeaponOrb) o;
                weaponOrb.effectAtStartOfTurnPostDraw();
            }
        }
    }

    @Override
    public float atDamageGive(float damage, DamageType type) {
        if (type.equals(DamageType.NORMAL)) {
            int weaponAttack = 0;
            for (WeaponOrb weaponOrb : getWeaponsToUse()) {
                weaponAttack += weaponOrb.getDamageToDeal();
            }
            return super.atDamageGive(damage, type) + weaponAttack;
        } else {
            return super.atDamageGive(damage, type);
        }
    }

    private List<WeaponOrb> getWeaponsToUse() {
        int numberOfWeaponsToUse = 1;
        if (AbstractDungeon.player.hasPower(WeaponProficiencyPower.POWER_ID)) {
            WeaponProficiencyPower weaponProficiencyPower = (WeaponProficiencyPower) AbstractDungeon.player.getPower(WeaponProficiencyPower.POWER_ID);
            numberOfWeaponsToUse += weaponProficiencyPower.amount;
        }
        if (AbstractDungeon.player.hasPower(SwordDancePower.POWER_ID)) {
            numberOfWeaponsToUse = Integer.MAX_VALUE;
        }

        List<WeaponOrb> weaponsToUseResult = new ArrayList<>();
        int counter = 0;
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof WeaponOrb) {
                WeaponOrb weaponOrb = (WeaponOrb) o;
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
