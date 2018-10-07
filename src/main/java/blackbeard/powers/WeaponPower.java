package blackbeard.powers;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


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
    public void onUseCard(AbstractCard card, UseCardAction action) {
        WeaponsToUseEnum weaponsToUse = WeaponsToUseEnum.ONLY_RIGHTMOST_WEAPON;
        if (AbstractDungeon.player.hasPower(AttackWithAllWeaponsPower.POWER_ID)) {
            weaponsToUse = WeaponsToUseEnum.ALL_WEAPONS;
        }

        if (!card.purgeOnUse && card.type == CardType.ATTACK) {
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof WeaponOrb) {
                    WeaponOrb weaponOrb = (WeaponOrb) o;
                    weaponOrb.use();
                    if (weaponsToUse.equals(WeaponsToUseEnum.ONLY_RIGHTMOST_WEAPON)) {
                        break;
                    }
                }
            }
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

        for(int i=0; i<weaponsToRemove.size(); i++){
            ((WeaponOrb)weaponsToRemove.get(i)).effectOnDestroy();
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
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    public int getNumberOfWeapons() {
        int result = 0;
        List<AbstractOrb> orbsList = AbstractDungeon.player.orbs;
        for (int i = 0; i < orbsList.size(); i++) {
            AbstractOrb orb = orbsList.get(i);
            if (orb instanceof WeaponOrb) {
                result ++;
            }
        }

        return result;
    }

    @Override
    public float atDamageGive(float damage, DamageType type) {
        WeaponsToUseEnum weaponsToUse = WeaponsToUseEnum.ONLY_RIGHTMOST_WEAPON;
        if (AbstractDungeon.player.hasPower(AttackWithAllWeaponsPower.POWER_ID)) {
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

        return type == DamageType.NORMAL ? damage + weaponAttack : damage;
    }
}
