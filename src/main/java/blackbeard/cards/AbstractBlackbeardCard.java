package blackbeard.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.core.Settings;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractBlackbeardCard extends CustomCard {

    private static List<String> smallerFontInPolishCardIds = Arrays.asList(IntimidatingStrike.ID, RearmingStrike.ID);
    private static List<String> smallerFontInRussianCardIds = Arrays.asList(BountyHunter.ID, FinalBarrage.ID, IntimidatingStrike.ID, Lifeboat.ID, MegaUpgrade.ID, RearmingStrike.ID, TacticalRetreat.ID, WeaponMastery.ID);
    private static List<String> smallerFontInEnglishCardIds = Arrays.asList(CloakAndCannonball.ID, HumongousCannonball.ID, IntimidatingStrike.ID, TheDrunkenSailor.ID, WeaponProficiency.ID);

    private float smallerFontSize = 17.0f;
    private float defaultFontSize = -1.0f;

    public AbstractBlackbeardCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public float getTitleFontSize() {
        switch (Settings.language) {
            case POL:
                return smallerFontInPolishCardIds.contains(this.cardID) ? smallerFontSize : defaultFontSize;
            case RUS:
                return smallerFontInRussianCardIds.contains(this.cardID) ? smallerFontSize : defaultFontSize;
            default:
                return smallerFontInEnglishCardIds.contains(this.cardID) ? smallerFontSize : defaultFontSize;
        }
    }

}
