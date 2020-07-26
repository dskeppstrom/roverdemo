package com.davidskeppstrom.roverdemo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.davidskeppstrom.roverdemo.model.Rover;
import com.davidskeppstrom.roverdemo.model.RoverImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ApplicationTest {

    private final Rover rover = new RoverImpl();

    @Before
    public void setUp() {
        rover.setCurrentX(5);
        rover.setCurrentY(4);
        rover.setMaxX(5);
        rover.setMaxY(5);
        rover.setDirection('E');
    }

    @Test
    public void tryRoverMoveTestCannotMoveEast() {
        Application.tryRoverMove(rover);

        assertEquals("Rover should not move along X axis", 5, rover.getCurrentX());
        assertEquals("Rover should not move along Y axis", 4, rover.getCurrentY());
        assertEquals("Rover should not change direction", 'E', rover.getDirection());
    }

    @Test
    public void turnRoverMoveTestCannotMoveWest() {
        rover.setCurrentY(1);
        rover.setCurrentX(0);
        rover.setDirection('W');
        Application.tryRoverMove(rover);

        assertEquals("Rover should not move along X axis", 0, rover.getCurrentX());
        assertEquals("Rover should not move along Y axis", 1, rover.getCurrentY());
        assertEquals("Rover should not change direction", 'W', rover.getDirection());
    }

    @Test
    public void turnRoverMoveTestCannotNorth() {
        rover.setCurrentY(5);
        rover.setCurrentX(4);
        rover.setDirection('N');
        Application.tryRoverMove(rover);

        assertEquals("Rover should not move along X axis", 4, rover.getCurrentX());
        assertEquals("Rover should not move along Y axis", 5, rover.getCurrentY());
        assertEquals("Rover should not change direction", 'N', rover.getDirection());
    }

    @Test
    public void turnRoverMoveTestCannotMoveSouth() {
        rover.setCurrentY(0);
        rover.setCurrentX(1);
        rover.setDirection('S');
        Application.tryRoverMove(rover);

        assertEquals("Rover should not move along X axis", 1, rover.getCurrentX());
        assertEquals("Rover should not move along Y axis", 0, rover.getCurrentY());
        assertEquals("Rover should not change direction", 'S', rover.getDirection());
    }

    @Test
    public void turnRoverMoveTestSouth() {
        rover.setCurrentY(1);
        rover.setCurrentX(1);
        rover.setDirection('S');
        Application.tryRoverMove(rover);

        assertEquals("Rover should not move along X axis", 1, rover.getCurrentX());
        assertEquals("Rover should move along Y axis", 0, rover.getCurrentY());
        assertEquals("Rover should not change direction", 'S', rover.getDirection());
    }

    @Test
    public void turnRoverMoveTestNorth() {
        rover.setCurrentY(1);
        rover.setCurrentX(1);
        rover.setDirection('N');
        Application.tryRoverMove(rover);

        assertEquals("Rover should not move along X axis", 1, rover.getCurrentX());
        assertEquals("Rover should move along Y axis", 2, rover.getCurrentY());
        assertEquals("Rover should not change direction", 'N', rover.getDirection());
    }

    @Test
    public void turnRoverMoveTestEast() {
        rover.setCurrentY(1);
        rover.setCurrentX(1);
        rover.setDirection('E');

        Application.tryRoverMove(rover);

        assertEquals("Rover should move along X axis", 2, rover.getCurrentX());
        assertEquals("Rover should not move along Y axis", 1, rover.getCurrentY());
        assertEquals("Rover should not change direction", 'E', rover.getDirection());
    }

    @Test
    public void turnRoverMoveTestWest() {
        rover.setCurrentY(1);
        rover.setCurrentX(1);
        rover.setDirection('W');

        Application.tryRoverMove(rover);

        assertEquals("Rover should move along X axis", 0, rover.getCurrentX());
        assertEquals("Rover should not move along Y axis", 1, rover.getCurrentY());
        assertEquals("Rover should not change direction", 'W', rover.getDirection());
    }

    @Test
    public void turnRoverLeftTest() {
        rover.setDirection('N');

        Application.turnRoverLeft(rover);
        assertEquals("Rover should be facing West", 'W', rover.getDirection());

        Application.turnRoverLeft(rover);
        assertEquals("Rover should be facing South", 'S', rover.getDirection());

        Application.turnRoverLeft(rover);
        assertEquals("Rover should be facing East", 'E', rover.getDirection());

        Application.turnRoverLeft(rover);
        assertEquals("Rover should be facing North", 'N', rover.getDirection());
    }

    @Test
    public void turnRoverRightTest() {
        rover.setDirection('N');

        Application.turnRoverRight(rover);
        assertEquals("Rover should be facing East", 'E', rover.getDirection());

        Application.turnRoverRight(rover);
        assertEquals("Rover should be facing South", 'S', rover.getDirection());

        Application.turnRoverRight(rover);
        assertEquals("Rover should be facing West", 'W', rover.getDirection());

        Application.turnRoverRight(rover);
        assertEquals("Rover should be facing North", 'N', rover.getDirection());
    }

    @Test
    public void checkAndSetRoverStartTest() throws Exception {
        final String roverStart = "1 5 E";

        Application.checkAndSetRoverStart(roverStart, rover);

        assertEquals("X coordinate is incorrect", 1, rover.getCurrentX());
        assertEquals("Y coordinate is incorrect", 5, rover.getCurrentY());
        assertEquals("Direction is incorrect", 'E', rover.getDirection());
    }

    @Test(expected = Exception.class)
    public void checkAndSetRoverStartFailure() throws Exception {
        final String roverStart = "1 Z E";

        Application.checkAndSetRoverStart(roverStart, rover);
    }

    @Test
    public void checkForInvalidGridTest() throws Exception {
        final String[] grid = {"6", "7"};
        Application.checkForInvalidGrid(grid, rover);

        assertEquals("Max X is incorrect", 6, rover.getMaxX());
        assertEquals("Max Y is incorrect", 7, rover.getMaxY());
    }

    @Test(expected = Exception.class)
    public void checkForInvalidGridTestFailure() throws Exception {
        final String[] grid = {"Z", "7"};
        Application.checkForInvalidGrid(grid, rover);
    }

    @Test
    public void isValidDirectionTest() throws Exception {
        assertTrue(Application.isValidDirection("E"));
        assertTrue(Application.isValidDirection("W"));
        assertTrue(Application.isValidDirection("N"));
        assertTrue(Application.isValidDirection("S"));
    }

    @Test(expected = Exception.class)
    public void isValidDirectionTestFailure() throws Exception {
        Application.isValidDirection("Z");
    }

    @Test
    public void isIntegerTest() {
        assertTrue("Should be an integer", Application.isInteger("1"));
        assertFalse("Should not be an integer", Application.isInteger("not an int!"));
    }

    @Test
    public void displayFinalRoverStateTest() {
        final ByteArrayOutputStream standardOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(standardOut));

        Application.displayFinalRoverState(rover);

        assertEquals("Final Rover State:\n5 4 E\n", standardOut.toString());
    }
}
