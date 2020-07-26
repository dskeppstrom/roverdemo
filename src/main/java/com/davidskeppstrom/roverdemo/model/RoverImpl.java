package com.davidskeppstrom.roverdemo.model;

public class RoverImpl implements Rover {

    private int currentX;
    private int currentY;
    private int maxX;
    private int maxY;

    private char direction;

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(final int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(final int currentY) {
        this.currentY = currentY;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(final int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(final int maxY) {
        this.maxY = maxY;
    }


    public char getDirection() {
        return direction;
    }

    public void setDirection(final char direction) {
        this.direction = direction;
    }
}
