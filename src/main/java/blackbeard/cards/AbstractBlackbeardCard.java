package blackbeard.cards;

import basemod.abstracts.CustomCard;
import blackbeard.TheBlackbeardMod;
import blackbeard.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractBlackbeardCard extends CustomCard {

    private static List<String> smallerFontInPolishCardIds = Arrays.asList(IntimidatingStrike.ID, RearmingStrike.ID);
    private static List<String> smallerFontInRussianCardIds = Arrays.asList(BountyHunter.ID, FinalBarrage.ID, IntimidatingStrike.ID, Lifeboat.ID, MegaUpgrade.ID, RearmingStrike.ID, TacticalRetreat.ID, WeaponMastery.ID);
    private static List<String> smallerFontInSimplifiedChineseCardIds = Collections.emptyList();
    private static List<String> smallerFontInEnglishCardIds = Arrays.asList(CloakAndCannonball.ID, HumongousCannonball.ID, IntimidatingStrike.ID, WeaponProficiency.ID);

    private float smallerFontSize = 17.0f;
    private float defaultFontSize = -1.0f;

    private static Texture cannonballTexture = TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("Cannonball"));
    private static Texture goldenCannonballTexture = TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("GoldenCannonball"));
    private static Texture humongousCannonballTexture = TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("HumongousCannonball"));

    public static Texture[] christmasCannonballTextures = {
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/80/Blue")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/80/Graphite")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/80/Green")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/80/Orange")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/80/Purple")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/80/Red")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/80/RoyalPurple")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/80/Silver")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/80/Turtoise"))
    };

    private static Texture christmasGoldenCannonballTexture = TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/80/Golden"));

    public static Texture[] christmasHumongousCannonballTextures = {
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/278/Blue")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/278/Graphite")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/278/Green")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/278/Orange")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/278/Purple")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/278/Red")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/278/RoyalPurple")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/278/Silver")),
            TextureLoader.getTexture(TheBlackbeardMod.getVfxImagePath("christmas/278/Turtoise"))
    };

    public AbstractBlackbeardCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public float getTitleFontSize() {
        switch (Settings.language) {
            case POL:
                return smallerFontInPolishCardIds.contains(this.cardID) ? smallerFontSize : defaultFontSize;
            case RUS:
                return smallerFontInRussianCardIds.contains(this.cardID) ? smallerFontSize : defaultFontSize;
            case ZHS:
                return smallerFontInSimplifiedChineseCardIds.contains(this.cardID) ? smallerFontSize : defaultFontSize;
            default:
                return smallerFontInEnglishCardIds.contains(this.cardID) ? smallerFontSize : defaultFontSize;
        }
    }

    public static Texture getCannonballTexture() {
        if (TheBlackbeardMod.shouldUseChristmasTheme()) {
            return christmasCannonballTextures[MathUtils.random(christmasCannonballTextures.length - 1)];
        }
        return cannonballTexture;
    }

    public static Texture getGoldenCannonballTexture() {
        if (TheBlackbeardMod.shouldUseChristmasTheme()) {
            return christmasGoldenCannonballTexture;
        }
        return goldenCannonballTexture;
    }

    public static Texture getHumongousCannonballTexture() {
        if (TheBlackbeardMod.shouldUseChristmasTheme()) {
            return christmasHumongousCannonballTextures[MathUtils.random(christmasHumongousCannonballTextures.length - 1)];
        }
        return humongousCannonballTexture;
    }

}
