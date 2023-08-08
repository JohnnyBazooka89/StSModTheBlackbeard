package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.effects.DamageCurvyEffect;
import blackbeard.effects.DamageLineEffect;
import blackbeard.enums.CardColorEnum;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RemoveScurvy extends AbstractBlackbeardCard {

    public static final String ID = "blackbeard:RemoveScurvy";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int HEAL_VALUE = 5;
    private static final int UPGRADED_HEAL_VALUE = 3;

    protected float offset = MathUtils.random(-180.0F, 180.0F);

    public RemoveScurvy() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.SELF);

        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = HEAL_VALUE;

        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 36; i++) {
            AbstractDungeon.effectList.add(new DamageLineEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(1F, 0.576F, 0.160F, 1), ((10 * i) + MathUtils.random(-10, 10) + offset)));
            if (i % 2 == 0) {
                AbstractDungeon.effectList.add(new DamageCurvyEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(1F, 0.576F, 0.160F, 1)));
            }
        }
        addToBot(new HealAction(p, p, this.magicNumber));
        addToBot(new RemoveDebuffsAction(p));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_HEAL_VALUE);
        }
    }
}
