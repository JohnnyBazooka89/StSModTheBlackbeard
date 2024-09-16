package blackbeard.potions;

import blackbeard.TheBlackbeardMod;
import blackbeard.actions.UpgradeWeaponsAction;
import blackbeard.enums.WeaponsToUseEnum;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static blackbeard.TheBlackbeardMod.makeID;

public class UpgradePotion extends AbstractBlackbeardPotion {

    private static final String ID = makeID("UpgradePotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private static final int POTENCY = 3;

    public UpgradePotion() {
        super(NAME, ID, PotionRarity.RARE, PotionSize.ANVIL, PotionColor.STRENGTH);
        this.isThrown = false;
    }

    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + this.potency + DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        Keyword weaponKeyword = TheBlackbeardMod.blackbeardKeywords.get(TheBlackbeardMod.WEAPON_KEYWORD);
        this.tips.add(new PowerTip(TipHelper.capitalize(weaponKeyword.NAMES[0]), weaponKeyword.DESCRIPTION));
        this.labOutlineColor = TheBlackbeardMod.BLACK.cpy();
    }

    @Override
    public void use(AbstractCreature unused) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new UpgradeWeaponsAction(this.potency, this.potency, WeaponsToUseEnum.ALL_WEAPONS));
        }
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return POTENCY;
    }

}