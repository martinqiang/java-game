package com.mq.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {
    public int width, height;
    public int[] pixels;


    public Sprite(String path){

        BufferedImage image;

        try {
            image = ImageIO.read(new File(path));

            this.width = image.getWidth();
            this.height = image.getHeight();
            this.pixels = new int[this.width * this.height];

            pixels = image.getRGB(0, 0, this.width, this.height, null, 0, this.width);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
