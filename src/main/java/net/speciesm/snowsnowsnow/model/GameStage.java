package net.speciesm.snowsnowsnow.model;

import lombok.Getter;
import lombok.Setter;
import net.speciesm.snowsnowsnow.model.particlesystem.ParticleSystem;

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

    private List<ParticleSystem> particlesystems;

    public GameStage(int width, int height) {
        this.width = width;
        this.height = height;

        particlesystems = new ArrayList<>();
    }

    public void addParticleSystem(ParticleSystem ps) {
        particlesystems.add(ps);
    }

    public void tick(double tickDelta) {
        particlesystems.forEach(p -> p.tick(tickDelta));
    }

    public int getParticleCount() {
        return particlesystems.stream().mapToInt(ParticleSystem::getCount).sum();
    }
}
