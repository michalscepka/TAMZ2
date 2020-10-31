package com.vsb.kru13.sokoban;

public class Level {

    public int spawnX, spawnY, width, height;
    public int[] layout;

    public Level(int[] layout, int height, int width, int spawnX, int spawnY) {
        this.layout = layout;
        this.height = height;
        this.width = width;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }
}
