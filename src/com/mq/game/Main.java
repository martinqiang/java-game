package com.mq.game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class Main {

    private static int xPos = 0;
    private static BufferStrategy bufferStrategy;
    private static int width, height;
    // Initialises the window with specified width/height
    public static void init(String title, int width, int height){
        Main.width = width;
        Main.height = height;

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(width, height));

        // graphics configuration -> describes the characteristics of the graphics destination i.e. monitor
//        GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment()
//                .getDefaultScreenDevice()
//                .getDefaultConfiguration();

        // canvas -> blank area that can be drawn to
        Canvas canvas = new Canvas();
        canvas.setBackground(Color.RED);

        frame.add(canvas);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // bufferStrategy -> mechanism to help organise complex memory on the canvas
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
    }
    // Render the JFrame
    public static void renderFrame() {
        // Render single frame
        do {
            do {
                Graphics graphics = bufferStrategy.getDrawGraphics();

                // Render to graphics
                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, Main.width, Main.height);
                graphics.setColor(Color.BLUE);
                if (xPos > width) xPos = 0;
                graphics.fillRect(xPos, 10, 10, 10);
                graphics.drawString("Hello World", xPos,100);
                xPos++;
                // Dispose the graphics
                graphics.dispose();
            } while (bufferStrategy.contentsRestored());
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }
    public static void generatePalette() {
        // 8 bit colour scheme 256 colours
    }
    public static void fill() {
        // Fill specified location of screen with specified colour
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
        long lastTime = System.nanoTime();
        double ticksPerSecond = 60;
        long nsPerSecond = 1000000000;
        double nsPerTick = nsPerSecond / ticksPerSecond;
        double delta = 0;
        int frames = 0, ticks = 0;
        double time = System.currentTimeMillis();

        while(isRunning){
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / nsPerTick;
            lastTime = currentTime;

            if (delta >= 1) {
                // Update

                // Render
                Main.renderFrame();


                frames++;
                delta--;
                // Computes and prints out FPS every second
                if (System.currentTimeMillis() - time >= 1000) {
                    System.out.println("FPS: " + frames);
                    time += 1000;
                    frames = 0;
                }
            }

        }
    }
    public static void main(String[] args) {
        init("Java Game", 512, 512);
        try {
            loop();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }



    }
}
