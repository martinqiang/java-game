package com.mq.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Renderer {
    // Renderer //
    public static final int GAME_WIDTH = 384, GAME_HEIGHT = 256;
    public static int[] pixels = new int[GAME_WIDTH * GAME_HEIGHT]; // WIDTH * HEIGHT of game, not window

    public static int[] generatePalette() {
        // 8-bit colour scheme 256 colours: RRR-GGG-BB
        // colour code is 24-bit
        // Mapping 3 bits to each bit of 8-bit colour
        // 000 000 000 000 000 000 000 000
        // RRR RRR RRR GGG GGG GGG BBB BBB
        int[] palette = new int[256];
        int i = 0;
        for (int r = 0; r < 8; r++) { // 8 values of r
            for (int g = 0; g < 8; g++) { // 8 values of g
                for (int b = 0; b < 4; b++){ // 4 values of b
                    // Generate the amount of colours for each colour spectrum
//                    int rr = ((r >> 2 & 1) == 1 ? 0b111 : 0b000) << 6 | ((r >> 1 & 1) == 1 ? 0b111 : 0b000) << 3 | ((r & 1) == 1 ? 0b111 : 0b000) << 0;
//                    int gg = ((g >> 2 & 1) == 1 ? 0b111 : 0b000) << 6 | ((g >> 1 & 1) == 1 ? 0b111 : 0b000) << 3 | ((g & 1) == 1 ? 0b111 : 0b000) << 0;
//                    int bb = ((b >> 1 & 1) == 1 ? 0b111 : 0b000) << 3 | ((b & 1) == 1 ? 0b111 : 0b000) << 0;
                    int rr = (r) * 73;
                    int gg = (g) * 73;
                    int bb = (b) * 21;
                    // Concatenate all the bits into 32 bit
                    palette[i] = (rr << 15) | (gg << 6) | (bb) << 0;
                    i++;
                }
            }
        }
        return palette;

    }
    // Function for generating the palette used in the game to use in creating graphics.
    public static void generatePaletteImage(){
        int[] rgb_palette = Renderer.generatePalette();
        BufferedImage palette = new BufferedImage(256,1, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < rgb_palette.length; i++){
            palette.setRGB(i,0,rgb_palette[i]);
        }
        try {
            File outputFile = new File("res/palette.jpg");
            ImageIO.write(palette, "jpg", outputFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void fillRect(int x, int y, int h, int w, int colour) {
        // Fill a rectangle at specified location of screen with specified colour
        for (int yy = y; yy < y + h && yy < Renderer.GAME_HEIGHT; yy++){
            for (int xx = x; xx < x + w && xx < Renderer.GAME_WIDTH; xx++){
                pixels[xx + yy * Renderer.GAME_WIDTH] = colour;
            }
        }
    }
    public static void renderSprite(Sprite sprite, int x, int y) {
        for (int xx = x, xs = 0; xx < x + sprite.width; xx++, xs++){
            for (int yy = y, ys = 0; yy < y + sprite.height; yy++, ys++){
                int pixel = sprite.pixels[ys*sprite.width + xs];
                Renderer.pixels[yy * Renderer.GAME_WIDTH + xx] = ((pixel>>24) == 0x00) ? -1 : pixel;

            }
        }
    }
}
