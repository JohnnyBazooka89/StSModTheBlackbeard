package blackbeard.orbs;

import blackbeard.TheBlackbeardMod;
import blackbeard.effects.DamageCurvyEffect;
import blackbeard.effects.DamageLineEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

import static blackbeard.TheBlackbeardMod.makeID;

public class SwordOfWisdomOrb extends AbstractWeaponOrb {

    public static final String ID = makeID("SwordOfWisdomOrb");
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTION = orbStrings.DESCRIPTION;

    public SwordOfWisdomOrb(int attack, int durability, boolean justAddedUsingAttackCard) {
        super(ID, NAME, DESCRIPTION[0], TheBlackbeardMod.getOrbImagePath(ID), attack, durability, justAddedUsingAttackCard);
        this.attack = attack;
        this.durability = durability;
    }

    @Override
    public void effectOnUse() {
        for (int i = 0; i < 36; i++) {
            AbstractDungeon.effectList.add(new DamageLineEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(1, 1, 0.352F, 1), ((10 * i) + MathUtils.random(-10, 10) + offset)));
            if (i % 2 == 0) {
                AbstractDungeon.effectList.add(new DamageCurvyEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(1, 1, 0.352F, 1)));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
    }
}
