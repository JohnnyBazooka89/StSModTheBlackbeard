package blackbeard.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class TextureLoader {

    public static final Logger logger = LogManager.getLogger(TextureLoader.class);
    private static HashMap<String, Texture> textures = new HashMap<>();

    public static Texture getTexture(final String textureString) {
        if (!textures.containsKey(textureString)) {
            loadTexture(textureString);
        }
        return textures.get(textureString);
    }

    private static void loadTexture(final String textureString) {
        Texture texture = new Texture(textureString);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        textures.put(textureString, texture);
    }
}
