package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.effects.DamageCurvyEffect;
import blackbeard.effects.DamageLineEffect;
import blackbeard.enums.CardColorEnum;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

public class BoostMorale extends AbstractBlackbeardCard {

    public static final String ID = "blackbeard:BoostMorale";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int GOLD_TO_GAIN = 5;
    private static final int CARDS_TO_DRAW = 1;
    private static final int ENERGY_TO_GET = 1;
    private static final int UPGRADE_PLUS_ENERGY_TO_GET = 1;

    protected float offset = MathUtils.random(-180.0F, 180.0F);

    public BoostMorale() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.RARE, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = CARDS_TO_DRAW;
        this.exhaust = true;

        this.tags.add(CardTags.HEALING); //It doesn't heal, but I don't want gaining Gold to be abused.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 36; i++) {
            AbstractDungeon.effectList.add(new DamageLineEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1), ((10 * i) + MathUtils.random(-10, 10) + offset)));
            if (i % 2 == 0) {
                AbstractDungeon.effectList.add(new DamageCurvyEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1)));
            }
        }
        AbstractDungeon.effectList.add(new RainingGoldEffect(GOLD_TO_GAIN, true));
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        addToBot(new GainGoldAction(GOLD_TO_GAIN));
        addToBot(new DrawCardAction(p, this.magicNumber));
        addToBot(new GainEnergyAction(ENERGY_TO_GET + (this.upgraded ? UPGRADE_PLUS_ENERGY_TO_GET : 0)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
