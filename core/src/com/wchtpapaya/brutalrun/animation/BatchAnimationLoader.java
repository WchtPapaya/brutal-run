package com.wchtpapaya.brutalrun.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class BatchAnimationLoader implements AnimationLoader {

    @Override
    public AnimationContainer loadAnimations(Texture sheet, String jsonFilePath) {
        Map<String, List<TextureRegion>> rawAnimationFrames = new HashMap<>();
        try {
            Reader reader = new FileReader(jsonFilePath);
            JsonValue root = new JsonReader().parse(reader);

            JsonValue frames = root.get("SubTexture");

            JsonValue current = frames.get(0);

            while (current != null) {
                String frameName = current.get("name").asString();
                log.info("Animation frame info parsed from json - {}", frameName);

                int x = current.get("x").asInt();
                int y = current.get("y").asInt();
                int width = current.get("width").asInt();
                int height = current.get("height").asInt();
                String animationName = frameName.substring(0, frameName.indexOf("_"));

                TextureRegion textureRegion = new TextureRegion(sheet, x, y, width, height);
                rawAnimationFrames.computeIfAbsent(animationName, k -> new ArrayList<>());
                rawAnimationFrames.get(animationName).add(textureRegion);
                current = current.next();
            }

            String spriteName = root.get("name").asString();
            AnimationContainer controller = new MapAnimationContainer(spriteName);

            rawAnimationFrames.forEach((name, textureRegions) -> {
                TextureRegion[] texture1dArray = textureRegions.toArray(new TextureRegion[0]);
                float frameDuration = 1.0f / textureRegions.size();
                Animation<TextureRegion> animation = new Animation<>(frameDuration, texture1dArray);
                controller.addAnimation(name, animation);
            });
            return controller;
        } catch (FileNotFoundException e) {
            log.error("Can not load Animation batch file {}", jsonFilePath);
            throw new RuntimeException("Error during loading the animation json file", e);
        }
    }
}
