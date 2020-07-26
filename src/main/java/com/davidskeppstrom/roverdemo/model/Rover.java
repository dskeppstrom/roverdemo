package com.davidskeppstrom.roverdemo.model;

public interface Rover {
    int getCurrentX();

    void setCurrentX(final int currentX);

    int getCurrentY();

    void setCurrentY(final int currentY);

    int getMaxX();

    void setMaxX(final int maxX);

    int getMaxY();

    void setMaxY(final int maxY);

    char getDirection();

    void setDirection(final char direction);
}
