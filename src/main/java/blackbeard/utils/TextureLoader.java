package blackbeard.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

import java.util.HashMap;

public class TextureLoader {

    private static HashMap<String, Texture> textures = new HashMap<>();

    public static Texture getTexture(final String texturePath) {
        Texture texture = textures.get(texturePath);
        if (texture != null) {
            return texture;
        } else {
            return loadTexture(texturePath);
        }
    }

    private static Texture loadTexture(final String texturePath) {
        Texture texture = new Texture(texturePath);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        textures.put(texturePath, texture);
        return texture;
    }
}
