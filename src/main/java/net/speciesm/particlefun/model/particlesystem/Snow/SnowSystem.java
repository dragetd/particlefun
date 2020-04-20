package net.speciesm.particlefun.model.particlesystem.Snow;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.speciesm.particlefun.engine.renderer.Renderer;
import net.speciesm.particlefun.model.particlesystem.GameObject;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * SnowSystem
 * Simulates falling snow.
 *
 * @author Draget draget@speciesm.net
 */
public class SnowSystem implements GameObject {
    @Data
    @AllArgsConstructor
    class Snowflake {
        private static final int GRAVITY = 150;
        private static final double DRAG = 0.01;

        private double x;
        private double y;
        private double velX;
        private double velY;

        private double curHealth;
        private double maxHealth;

        void fall(double tickDelta) {
            if (curHealth <= 0) {
                return;
            }

            // external forces
            velY += GRAVITY * tickDelta;
            velX *= 1 - (DRAG * tickDelta);
            velY *= 1 - (DRAG * tickDelta);

            // move it
            x += velX * tickDelta;
            y += velY * tickDelta;

            // snow at new position?
            if (getPixel((int) x, (int) y) != 0) {
                // revert movement
                x -= velX * tickDelta;
                y -= velY * tickDelta;

                // ontop of existing snow
                if (velY > 0 && getPixel((int) x, (int) y + 1) != 0) {
                    putDown(x, y);
                } else {
                    moveToSideIfFree();
                    velX *= 0.8;
                    velY *= 0.7;
                }

                /*if (velY < 0) {
                    // hit snow from below
                    x -= velX * tickDelta;
                    y -= velY * tickDelta;
                    velY = 0;
                } else {
                    // going down
                    if (!moveToSideIfFree()) {
                        //x -= velX * tickDelta;
                        for (int maxTries = 3; maxTries > 0; maxTries--) {
                            if (getPixel((int) x, (int) y) != 0 && y > 1) {
                                y -= 1;
                                moveToSideIfFree();
                            } else {
                                break;
                            }
                        }
                    }
                    putDown(x, y);
                }*/
            }

            if (x < 0) x = 0;
            if (x > WIDTH) x = WIDTH;

            if (y > HEIGHT - 1) {
                y = HEIGHT - 1;
                putDown(x, y);
            }

            curHealth -= tickDelta;
        }

        private boolean moveToSideIfFree() {
            if (getPixel((int) x - 1, (int) y) == 0) {
                x -= 1;
            } else if (getPixel((int) x + 1, (int) y) == 0) {
                x += 1;
            } else {
                return false;
            }
            return true;
        }

        private void putDown(double x, double y) {
            setPixel((int) x, (int) y, (byte) 255, (byte) 255, (byte) 255);
            curHealth = -1;
        }
    }

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private List<Snowflake> snowflakes = new ArrayList<>();

    private PixelFormat<ByteBuffer> format;
    private volatile byte[] snowImageData;
    private WritableImage outImage = new WritableImage(WIDTH, HEIGHT);

    public SnowSystem() {
        format = PixelFormat.getByteRgbInstance();
        snowImageData = new byte[WIDTH * HEIGHT * 3];
    }

    public void addFlake(double x, double y, double velX, double velY, int boundX, int boundY, double maxHealth) {
        snowflakes.add(new Snowflake(x, y, velX, velY, maxHealth, maxHealth));
    }

    public void tick(double tickDelta) {
        List<Snowflake> removeList = new LinkedList<>();
        snowflakes.parallelStream().forEach(s -> {
            s.fall(tickDelta);
            if (s.getCurHealth() <= 0) {
                removeList.add(s);
            }
        });
        snowflakes.removeAll(removeList);
    }

    public boolean isEmpty() {
        return snowflakes.isEmpty();
    }

    public int getCount() {
        return snowflakes.size();
    }

    private void setPixel(int x, int y, byte r, byte g, byte b) {
        if ((x < WIDTH) && (y < HEIGHT)) {
            snowImageData[y * WIDTH * 3 + x * 3] = r;
            snowImageData[y * WIDTH * 3 + x * 3 + 1] = g;
            snowImageData[y * WIDTH * 3 + x * 3 + 2] = b;
        }
    }

    private byte getPixel(int x, int y) {
        if ((x > 0) && (x < WIDTH) && (y > 0) && (y < HEIGHT)) {
            return snowImageData[y * WIDTH * 3 + x * 3];
        } else if (y > HEIGHT) {
            return (byte) 255;
        }
        return 0;
    }

    @Override
    public void draw(Renderer renderer) {
        // draw fixed snow
        outImage.getPixelWriter().setPixels(
                0, 0, WIDTH, HEIGHT, format, snowImageData, 0, WIDTH * 3);
        renderer.drawImage(outImage, 0, 0);

        snowflakes.forEach(s -> {
            renderer.drawPixel((int) s.getX(), (int) s.getY(), Color.WHITE);
        });
    }
}
