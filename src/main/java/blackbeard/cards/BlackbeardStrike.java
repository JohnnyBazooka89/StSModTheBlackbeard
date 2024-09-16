package blackbeard.cards;

import basemod.helpers.BaseModCardTags;
import blackbeard.TheBlackbeardMod;
import blackbeard.enums.CardColorEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackbeard.TheBlackbeardMod.makeID;

public class BlackbeardStrike extends AbstractBlackbeardCard {
    public static final String ID = makeID("Strike");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 6;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 3;

    public BlackbeardStrike() {
        super(ID, NAME, TheBlackbeardMod.getCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.BASIC, AbstractCard.CardTarget.ENEMY);

        this.baseDamage = this.damage = ATTACK_DMG;

        this.tags.add(CardTags.STRIKE);
        this.tags.add(CardTags.STARTER_STRIKE);
        this.tags.add(BaseModCardTags.BASIC_STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }
}
