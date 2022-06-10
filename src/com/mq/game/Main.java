package com.mq.game;

import com.sun.source.tree.CompilationUnitTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.*;
import java.util.Arrays;

public class Main {

    private static int xPos;

    // Window //
    // Frame buffer image
    private static BufferedImage backBuffer;

    private static BufferStrategy bufferStrategy;
    private static int width, height;
    // Initialises the window with specified width/height

    private static int[] rgb_palette;
    // Renderer //
    public static int gameWidth = 256, gameHeight = 256;
    public static int pixels[] = new int[gameWidth * gameHeight]; // WIDTH * HEIGHT of game, not window

    public static void init(String title, int width, int height){
        Main.width = width;
        Main.height = height;

        xPos = 0;

        // graphics configuration -> describes the characteristics of the graphics destination i.e. monitor
        // To ensure created graphics suit the graphics destination
        GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        Main.backBuffer = graphicsConfiguration.createCompatibleImage(Main.width, Main.height);
//        Main.backBuffer.getRaster().getDataBuffer();

        Main.rgb_palette = generatePalette();

        // Frame -> the window
        JFrame frame = new JFrame(title, graphicsConfiguration);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(width, height));

        // Scale the window when window is resized
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Main.width = frame.getWidth();
                Main.height = frame.getHeight();
            }
        });
        // canvas -> blank area that can be drawn to
        Canvas canvas = new Canvas();
        canvas.setBackground(Color.RED);

        frame.add(canvas);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // bufferStrategy -> mechanism to help organise complex memory on the canvas
        // Two buffers, one for the current one on the canvas, the other one to draw to before updating.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
    }
    // Render the JFrame
    public static void renderFrame() {
        // Render single frame
        do {
            do {
                // Retrieving graphics from buffer strategy
                // Drawing to the graphics goes into one of the buffers.
                Graphics graphics = bufferStrategy.getDrawGraphics();



                // Draw to graphics
                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, Main.width, Main.height);

                for (int i = 0; i < pixels.length; i++){ // For every pixel in pixels
                    int x = i % gameWidth;
                    int y = i / gameHeight;
                    backBuffer.setRGB(x,y,rgb_palette[pixels[i]]);
                }

//                double rendererAspect = (double) gameWidth / (double) gameHeight,
//                        windowAspect = (double) width / (double) height,
//                        scaleFactor = windowAspect > rendererAspect ?
//                                (double) height / (double) gameHeight :
//                                (double) width / (double) gameWidth;
//
//                int rw = (int) (gameWidth * scaleFactor), rh = (int) (gameHeight * scaleFactor);
//                graphics.drawImage(
//                        backBuffer,(Main.width - rw)/2, (Main.height-rh)/2,rw,rh,null);

                graphics.drawImage(
                        backBuffer,0, 0,Main.width,Main.height,null);

                // Dispose the graphics
                graphics.dispose();
            } while (bufferStrategy.contentsRestored());
            // Make the buffer that was drawn to, the current buffer for the canvas
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }
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
    public static void fill() {
        // Fill specified location of screen with specified colour
    }
    public static void tick() {
        // To run every tick

    }
    public static void render() {

        int i = 0;
        for (int x = 0; x < gameWidth; x++) {
            for (int y = 0; y < gameHeight; y++) {
                pixels[i] = y;
                i++;
            }
        }
        // Render test for diagonal moving all palette colours
//        for (int i = 0; i < pixels.length; i++){
//            pixels[i] = xPos;
//            xPos = (xPos < 255) ? xPos + 1 : 1;
//        }
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
            Main.renderFrame(); // Render
            frames++;

            // Each second
            if ((currentTime - lastSecond) >= Time.NS_PER_SECOND) {
                System.out.println("FPS:" + frames + "|" + "TPS:" + ticks);
                lastSecond = currentTime;
                frames = 0;
                ticks = 0;
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
        init("Java Game", 256, 256);
        try {
            loop();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }



    }
}
