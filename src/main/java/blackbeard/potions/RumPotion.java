package blackbeard.potions;

import basemod.abstracts.CustomPotion;
import blackbeard.TheBlackbeardMod;
import blackbeard.powers.ResistancePower;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class RumPotion extends CustomPotion {

    private static final String ID = "blackbeard:RumPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private static final int POTENCY = 2;

    public RumPotion() {
        super(NAME, ID, PotionRarity.UNCOMMON, PotionSize.H, PotionColor.ELIXIR);
        this.isThrown = false;
    }

    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        Keyword resistanceKeyword = TheBlackbeardMod.keywords.get(TheBlackbeardMod.RESISTANCE_KEYWORD);
        this.tips.add(new PowerTip(TipHelper.capitalize(resistanceKeyword.NAMES[0]), resistanceKeyword.DESCRIPTION));
    }

    @Override
    public void use(AbstractCreature unused) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResistancePower(AbstractDungeon.player, this.potency), this.potency));
        }
    }

    public AbstractPotion makeCopy() {
        return new RumPotion();
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return POTENCY;
    }
}