package com.mq.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
    // Window //
    public static JFrame frame;
    public static Canvas canvas;
    // Frame buffer image
    public static BufferedImage backBuffer;

    private static BufferStrategy bufferStrategy;
    public static int width, height;
    // Initialises the window with specified width/height

    private static int[] rgb_palette;


    public static void init(String title, int width, int height){
        Window.width = width;
        Window.height = height;

        // graphics configuration -> describes the characteristics of the graphics destination i.e. monitor
        // To ensure created graphics suit the graphics destination
        GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        Window.backBuffer = graphicsConfiguration.createCompatibleImage(Renderer.GAME_WIDTH, Renderer.GAME_HEIGHT);
//        Main.backBuffer.getRaster().getDataBuffer();

        Window.rgb_palette = Renderer.generatePalette();

        // Frame -> the window
        frame = new JFrame(title, graphicsConfiguration);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(width, height));


        // Scale the window when window is resized
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Window.width = canvas.getWidth();
                Window.height = canvas.getHeight();
            }
        });
        frame.setIgnoreRepaint(true);
        // canvas -> blank area that can be drawn to
        canvas = new Canvas();
        canvas.setBackground(Color.BLACK);
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setSize(width, height);
        canvas.setIgnoreRepaint(true);
        canvas.addKeyListener(Keyboard.getListener());
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
                graphics.fillRect(0, 0, Window.width, Window.height);

                for (int i = 0; i < Renderer.pixels.length; i++){ // For every pixel in pixels
                    int x = i % Renderer.GAME_WIDTH;
                    int y = i / Renderer.GAME_WIDTH;
                    backBuffer.setRGB(x,y,Renderer.pixels[i]);
                }

                // Keep the game screen the same aspect ratio if window is resized.
                double rendererAspect = (double) Renderer.GAME_WIDTH / (double) Renderer.GAME_HEIGHT,
                        windowAspect = (double) Window.width / (double) Window.height,
                        scaleFactor = windowAspect > rendererAspect ?
                                (double) Window.height / (double) Renderer.GAME_HEIGHT :
                                (double) Window.width / (double) Renderer.GAME_WIDTH;

                int rw = (int) (Renderer.GAME_WIDTH * scaleFactor), rh = (int) (Renderer.GAME_HEIGHT * scaleFactor);
                graphics.drawImage(
                        backBuffer,(Window.width-rw)/2, (Window.height-rh)/2, rw, rh,null);

//                graphics.drawImage(
//                        backBuffer,0, 0, Window.width, Window.height,null);

                // Dispose the graphics
                graphics.dispose();
            } while (bufferStrategy.contentsRestored());
            // Make the buffer that was drawn to, the current buffer for the canvas
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }
}
