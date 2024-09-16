package blackbeard.cards;

import blackbeard.TheBlackbeardMod;
import blackbeard.enums.CardColorEnum;
import blackbeard.powers.ArmorUpPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackbeard.TheBlackbeardMod.makeID;

public class ArmorUp extends AbstractBlackbeardCard {
    public static final String ID = makeID("ArmorUp");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_FOR_WEAPON_ATTACK = 2;
    private static final int UPGRADE_PLUS_BLOCK_FOR_WEAPON_ATTACK = 1;

    public ArmorUp() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.POWER,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.UNCOMMON, CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = BLOCK_FOR_WEAPON_ATTACK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ArmorUpPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_BLOCK_FOR_WEAPON_ATTACK);
        }
    }
}
