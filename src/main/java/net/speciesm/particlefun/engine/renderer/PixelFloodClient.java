package net.speciesm.particlefun.engine.renderer;


import javafx.scene.paint.Color;
import lombok.ToString;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class PixelFloodClient {
    private PrintWriter writer;
    private boolean skipFlush = false;

    public PixelFloodClient(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        writer = new PrintWriter(socket.getOutputStream(), false);
    }

    private String colorToHex(Color color) {
        if (color.getBrightness() > 0.5) {
            //Integer.toHexString((int)(Math.random() * 255 * 255 * 255));
            return Integer.toHexString((int) (Math.random() * 255 * 255 * 255));
            //return "ffffff";
        }
        else {
            return "000000";
        }
        //return Integer.toHexString((int) (color.getRed() * 255)).substring(2) +
        //        Integer.toHexString((int) (color.getGreen() * 255)).substring(2) +
        //        Integer.toHexString((int) (color.getBlue() * 255)).substring(2);
    }

    public void flush(){
        writer.flush();
    }

    public void setPixel(int x, int y, Color color) {
        if (x < 1 || x > 1918 || y < 1 || y > 1078) {
            return;
        }
        String out = String.format("PX %d %d %s", x, y, colorToHex(color));
        writer.println(out);
        //if (!skipFlush) writer.flush();
    }

    public void drawRect(int startX, int startY, int width, int height, Color color) {
        skipFlush = true;
        for (int x = startX; x <= startX + width; x++) {
            for (int y = startY; y <= startY + height; y++) {
                setPixel(x, y, color);
            }
        }
        System.out.println("Drawing rect");
        //skipFlush = false;
        //writer.flush();
    }

}
