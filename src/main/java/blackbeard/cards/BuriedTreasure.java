package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.enums.CardColorEnum;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

import static blackbeard.TheBlackbeardMod.makeID;

public class BuriedTreasure extends AbstractBlackbeardCard {
    public static final String ID = makeID("BuriedTreasure");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int GOLD_TO_GAIN = 20;
    private static final int UPGRADE_PLUS_GOLD_TO_GAIN = 5;

    public BuriedTreasure() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = GOLD_TO_GAIN;

        this.exhaust = true;

        this.tags.add(CardTags.HEALING); //It doesn't heal, but I don't want gaining Gold to be abused.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("GOLD_GAIN");
        AbstractDungeon.effectList.add(new RainingGoldEffect(this.magicNumber, true));
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        addToBot(new GainGoldAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_GOLD_TO_GAIN);
        }
    }
}
