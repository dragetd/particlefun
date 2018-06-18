package net.speciesm.particlefun.model.particlesystem;

import net.speciesm.particlefun.engine.Renderer;

/**
 * GameObject
 *
 * @author Draget draget@speciesm.net
 */
public interface GameObject {

    /**
     * Return the number of particles currently simulated by this system
     *
     * @return
     */
    int getCount();

    default void tick(double tickDelta) {
    }

    default void draw(Renderer renderer) {
    }
}
