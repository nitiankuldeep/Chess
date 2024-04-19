package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Board {
    final int column=8;
    final int row=8;
    public static final int size=104;
    public static final int halfSize=(size/2);

    public void make(Graphics2D g2) {
        int c1 = 1;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < column; c++) {
                if (c1 == 0) {
                    g2.setColor(new Color(145, 90, 1));
                    c1 = 1;
                } else {
                    c1 = 0;
                    g2.setColor(new Color(255, 218, 163));
                }
                g2.fillRect(c * size, r * size, size, size);
            }
            if (c1 == 0) {
                c1 = 1;
            } else {
                c1 = 0;
            }
        }
    }
}
