package blackbeard.powers;

import blackbeard.powers.interfaces.OnMonsterDeathPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import static blackbeard.TheBlackbeardMod.makeID;

public class GreedPower extends AbstractPower implements OnMonsterDeathPower {
    public static final String POWER_ID = makeID("GreedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GreedPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.loadRegion("thievery");
        this.type = PowerType.BUFF;
        this.updateDescription();
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if (!m.hasPower(MinionPower.POWER_ID)) {
            AbstractDungeon.player.gainGold(this.amount);

            for(int i = 0; i < this.amount; ++i) {
                AbstractDungeon.effectList.add(new GainPennyEffect(owner, m.hb.cX, m.hb.cY, owner.hb.cX, owner.hb.cY, true));
            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}