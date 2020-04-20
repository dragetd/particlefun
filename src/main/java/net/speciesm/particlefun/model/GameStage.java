package net.speciesm.particlefun.model;

import lombok.Getter;
import lombok.Setter;
import net.speciesm.particlefun.model.particlesystem.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * GameStage
 * Represents the main game world.
 *
 * @author Draget draget@speciesm.net
 */
public class GameStage {
    @Getter @Setter private int width;
    @Getter @Setter private int height;

    @Getter private List<GameObject> gameObjects;

    public GameStage(int width, int height) {
        this.width = width;
        this.height = height;

        gameObjects = new ArrayList<>();
    }

    public void addParticleSystem(GameObject ps) {
        gameObjects.add(ps);
    }

    public void tick(double tickDelta) {
        gameObjects.forEach(gameObject -> gameObject.tick(tickDelta));
    }

    public int getParticleCount() {
        return gameObjects.stream().mapToInt(GameObject::getCount).sum();
    }
}
