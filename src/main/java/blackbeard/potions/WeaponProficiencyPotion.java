package blackbeard.potions;

import basemod.abstracts.CustomPotion;
import blackbeard.TheBlackbeardMod;
import blackbeard.powers.WeaponProficiencyPower;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.SacredBark;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class WeaponProficiencyPotion extends CustomPotion {

    private static final String ID = "blackbeard:WeaponProficiencyPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private static final int POTENCY = 1;

    public WeaponProficiencyPotion() {
        super(NAME, ID, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionColor.FEAR);
        this.isThrown = false;
    }


    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        if (this.potency == 1) {
            this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[2];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        Keyword weaponKeyword = TheBlackbeardMod.keywords.get(TheBlackbeardMod.WEAPON_KEYWORD);
        this.tips.add(new PowerTip(TipHelper.capitalize(weaponKeyword.NAMES[0]), weaponKeyword.DESCRIPTION));
    }

    @Override
    public void use(AbstractCreature unused) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeaponProficiencyPower(AbstractDungeon.player, this.potency), this.potency));
        }
    }

    public AbstractPotion makeCopy() {
        return new WeaponProficiencyPotion();
    }

    @Override
    public int getPotency(int ascensionLevel) {
        if (AbstractDungeon.player == null) {
            return POTENCY;
        } else {
            return AbstractDungeon.player.hasRelic(SacredBark.ID) ? 2 * POTENCY : POTENCY;
        }
    }
}