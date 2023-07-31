package blackbeard.cards;

import blackbeard.CannonballDamageInfo;
import blackbeard.TheBlackbeardMod;
import blackbeard.actions.ShootAnythingAction;
import blackbeard.enums.CardColorEnum;
import blackbeard.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HumongousCannonball extends AbstractBlackbeardCard {
    public static final String ID = "blackbeard:HumongousCannonball";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int ATTACK_DMG = 36;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 12;

    public HumongousCannonball() {
        super(ID, NAME, getCardImagePathBasedOnUserLanguage(), COST, DESCRIPTION, CardType.ATTACK,
                CardColorEnum.BLACKBEARD_BLACK, CardRarity.RARE, CardTarget.ENEMY);

        this.baseDamage = this.damage = ATTACK_DMG;
        this.exhaust = true;

        this.tags.add(CardTagsEnum.CANNONBALL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ShootAnythingAction(m, getHumongousCannonballTexture(), false));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new CannonballDamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }

    private static String getCardImagePathBasedOnUserLanguage() {
        switch (Settings.language) {
            case POL:
            case RUS:
            case ZHS:
                return TheBlackbeardMod.getCardImagePath(ID + Settings.language.name());
            default:
                return TheBlackbeardMod.getCardImagePath(ID + Settings.GameLanguage.ENG.name());
        }
    }
}
