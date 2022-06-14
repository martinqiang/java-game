package com.mq.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard {
    public static final int KEY_UP = KeyEvent.VK_UP;
    public static final int KEY_DOWN = KeyEvent.VK_DOWN;

    public static boolean upPressed;
    public static boolean downPressed;

    public static void tick() {

    }
    public static KeyListener getListener() {
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KEY_UP){
                    System.out.println("Up Pressed!");
                    upPressed = true;
                }
                if (e.getKeyCode() == KEY_DOWN){
                    System.out.println("Down Pressed!");
                    downPressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KEY_UP){
                    upPressed = false;
                }
                if (e.getKeyCode() == KEY_DOWN){
                    downPressed = false;
                }
            }
        };
    }
}
