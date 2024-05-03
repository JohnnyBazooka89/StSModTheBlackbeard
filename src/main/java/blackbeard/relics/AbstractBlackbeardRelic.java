package blackbeard.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

public abstract class AbstractBlackbeardRelic extends CustomRelic {

    public AbstractBlackbeardRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, texture, outline, tier, sfx);
    }

}

