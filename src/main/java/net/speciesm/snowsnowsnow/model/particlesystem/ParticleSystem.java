package net.speciesm.snowsnowsnow.model.particlesystem;

import net.speciesm.snowsnowsnow.model.Drawable;

/**
 * ParticleSystem
 *
 * @author Draget draget@speciesm.net
 */
public interface ParticleSystem extends Drawable {

    /**
     * Return the number of particles currently simulated by this system
     *
     * @return
     */
    int getCount();

    void tick(double tickDelta);
}
