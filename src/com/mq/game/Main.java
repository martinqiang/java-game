package com.mq.game;

import com.sun.source.tree.CompilationUnitTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.*;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static int xPos = 0;
    private static Player player;

    public static void init() {
        player = new Player();
    }

    public static void tick() {
        // To run every tick
        // Render test for palette colours displayed horizontally.
//        int colour = 0;
//        for (int i = 0; i < Renderer.pixels.length; i++){
////            Renderer.fillRect(i%Renderer.GAME_WIDTH,i/Renderer.GAME_WIDTH,1,1,xPos);
//            if (i % Renderer.GAME_WIDTH == 0){
//                vert++;
//                xPos = vert;
//            }
//            xPos = xPos < 255 ? xPos + 1 : 0;
//            Renderer.pixels[i] = xPos;
//        }
        // Render test for diagonal moving all palette colours
//        int vert = 0;
//        int colour = 0;
//        for (int i = 0; i < Renderer.pixels.length; i++){
////            Renderer.fillRect(i%Renderer.GAME_WIDTH,i/Renderer.GAME_WIDTH,1,1,xPos);
//            if (i % Renderer.GAME_WIDTH == 0){
//                vert = xPos;
//                if (xPos < 255) {
//                    xPos++;
//                } else {
//                    xPos = 1;
//                }
//            }
//            vert = vert < 255 ? vert + 1 : 1;
//            Renderer.pixels[i] = vert;
//
//        }
        // Render test for diagonal moving all palette colours
//        for (int i = 0; i < pixels.length; i++){
//            pixels[i] = xPos;
//            xPos = (xPos < 255) ? xPos + 1 : 1;
//        }
//        Renderer.fillRect(0,0,255,255,10);

        //player.move(1,1);
        player.tick();

    }
    public static void render() {
        Renderer.fillRect(0,0,256,256,0xFFFFFF);
        player.render();
    }
    public static void loop(
            // Runnable init,
            // Runnable destroy,
            // Runnable tick,
            // Runnable update,
            // Runnable render
    ){

        boolean isRunning = true;

        // Initialise game loop time variables
        long lastTime = Time.currentTime();
        double lastSecond = Time.currentTime();
        int frames = 0, ticks = 0;

        double delta = 0;

        while(isRunning){
            long currentTime = Time.currentTime();

            delta += (currentTime - lastTime) / Time.NS_PER_TICK;
            lastTime = currentTime;
            while (delta >= 1) { // Each tick
                // Update
                Main.tick();

                ticks++;
                delta--;

            }

            // update()
            Main.render();

            // Each frame
            Window.renderFrame(); // Render
            frames++;

            // Each second
            if ((currentTime - lastSecond) >= Time.NS_PER_SECOND) {
                System.out.println("FPS:" + frames + "|" + "TPS:" + ticks);
                lastSecond = currentTime;
                frames = 0;
                ticks = 0;
//                System.out.println("Height " + Window.frame.getHeight() + " Width " + Window.frame.getWidth());
//                System.out.println("Height " + Window.height + " Width " + Window.width);
//                System.out.println("Height " + Window.backBuffer.getHeight() + " Width " + Window.backBuffer.getWidth());
            }

            // Sleep to keep FPS consistent
            try {
                long sleepTime = ((Time.NS_PER_SECOND/Time.TARGET_FPS) - (Time.currentTime() - lastTime)) / Time.NS_PER_MS;
                Thread.sleep(sleepTime > 0 ? sleepTime : 0); // if sleep > 0, return sleep otherwise 0;
            } catch (InterruptedException e) {
               e.printStackTrace();
            }

        }
    }
    public static void main(String[] args) {
        Window.init("Java Game", 384, 256);
        init();
        //Renderer.generatePaletteImage();
        try {
            loop();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }



    }
}
