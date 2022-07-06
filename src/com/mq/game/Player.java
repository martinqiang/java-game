package com.mq.game;

public class Player {
    // Location of player
    public int x, y;

    private Sprite sprite;

    private int height, width;

    public Player(){
        this.x = 100;
        this.y = 100;
        this.sprite = new Sprite("res/cat-wizard.png");
        this.height = this.sprite.height;
        this.width = this.sprite.width;
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
        int xx = this.x + dx;
        int yy = this.y + dy;
        if (xx >= 0 && xx < Renderer.GAME_WIDTH - this.width/2 && yy >= 0 && yy < Renderer.GAME_HEIGHT - this.height) {
            this.x = xx;
            this.y = yy;
        }
    }
}
