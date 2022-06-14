package com.mq.game;

public class Player {
    // Location of player
    public int x, y;

    private Sprite sprite;

    public Player(){
        this.x = 100;
        this.y = 100;
        this.sprite = new Sprite("res/cat-wizard.png");
    }

    public void render() {
        Renderer.renderSprite(this.sprite, this.x, this.y);
    }

    public void tick() {
        if (Keyboard.upPressed){
            move(0,-2);
        }
        if (Keyboard.downPressed){
            move(0,2);
        }
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
}
