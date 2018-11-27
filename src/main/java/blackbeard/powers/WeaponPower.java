package blackbeard.powers;//

import blackbeard.enums.WeaponsToUseEnum;
import blackbeard.orbs.WeaponOrb;
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
            WeaponsToUseEnum weaponsToUse = WeaponsToUseEnum.ONLY_RIGHTMOST_WEAPON;
            if (AbstractDungeon.player.hasPower(SwordDancePower.POWER_ID)) {
                weaponsToUse = WeaponsToUseEnum.ALL_WEAPONS;
            }
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof WeaponOrb) {
                    WeaponOrb weaponOrb = (WeaponOrb) o;
                    if (!weaponOrb.isJustAddedUsingAttackCard()) {
                        weaponOrb.use(action.target);
                    }
                    if (weaponsToUse.equals(WeaponsToUseEnum.ONLY_RIGHTMOST_WEAPON)) {
                        break;
                    }
                }
            }
            clearJustAddedUsingAttackCardFlagInWeapons();
            destroyWeaponsWithZeroDurability();
        }
    }

    public void destroyWeaponsWithZeroDurability() {
        List<AbstractOrb> weaponsToRemove = new ArrayList<>();

        boolean hasAtLeastOneWeaponLeft = false;

        List<AbstractOrb> orbsList = AbstractDungeon.player.orbs;
        for (int i = 0; i < orbsList.size(); i++) {
            AbstractOrb orb = orbsList.get(i);
            if (orb instanceof WeaponOrb) {
                WeaponOrb weaponOrb = (WeaponOrb) orb;
                if (weaponOrb.getDurability() == 0) {
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
            WeaponsToUseEnum weaponsToUse = WeaponsToUseEnum.ONLY_RIGHTMOST_WEAPON;
            if (AbstractDungeon.player.hasPower(SwordDancePower.POWER_ID)) {
                weaponsToUse = WeaponsToUseEnum.ALL_WEAPONS;
            }

            int weaponAttack = 0;
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof WeaponOrb) {
                    WeaponOrb weaponOrb = (WeaponOrb) o;
                    weaponAttack += weaponOrb.getAttack();
                    if (weaponsToUse.equals(WeaponsToUseEnum.ONLY_RIGHTMOST_WEAPON)) {
                        break;
                    }
                }
            }
            return super.atDamageGive(damage, type) + weaponAttack;
        } else {
            return super.atDamageGive(damage, type);
        }
    }
}
