package com.davidskeppstrom.roverdemo;

import java.util.Scanner;

import com.davidskeppstrom.roverdemo.model.Rover;
import com.davidskeppstrom.roverdemo.model.RoverImpl;

public class Application {

    public static void main(final String[] args) throws Exception {
        final Scanner scanner = new Scanner(System.in);
        final Rover rover = new RoverImpl();

        System.out.println("Welcome to the Mars Rover Simulation!");

        /* prompt user for grid size */
        System.out.println("Enter Grid size in the format 'x y' (no single quotes, Q to exit):");
        final String gridSize = scanner.nextLine();

        /* parse the grid size and validate */
        if (!"q".equalsIgnoreCase(gridSize)) {
            final String[] grid = gridSize.split(" ");
            checkForInvalidGrid(grid, rover);
        } else {
            System.exit(0);
        }

        while (true) {
            /* while input is not 'q' or 'Q', prompt for rover starting position */
            System.out.println("Enter rover starting position and direction(N S E W) in the format 'x y d' (no single "
                    + "quotes, Q to exit):");
            final String roverStart = scanner.nextLine();

            checkAndSetRoverStart(roverStart, rover);

            /* while input is not 'q' or 'Q', prompt for rover starting position */
            System.out.println(
                    "Enter rover movement string, no spaces: (L turns left, R turns right, M moves one unit, Q "
                            + "to exit)");
            final String roverPath = scanner.nextLine();

            executeRoverPath(roverPath, rover);

            displayFinalRoverState(rover);
        }
    }

    protected static void displayFinalRoverState(final Rover rover) {
        System.out.println("Final Rover State:");
        System.out.println(rover.getCurrentX() + " " + rover.getCurrentY() + " " + rover.getDirection());
    }

    protected static void executeRoverPath(final String roverPath, final Rover rover) {
        final String[] roverPathArray = roverPath.split("");

        for (final String roverMove : roverPathArray) {
            switch (roverMove.toUpperCase()) {
                case "Q":
                    System.exit(0);
                    break;
                case "L":
                    turnRoverLeft(rover);
                    break;
                case "R":
                    turnRoverRight(rover);
                    break;
                case "M":
                    tryRoverMove(rover);
                    break;
                default:
                    System.out.println("Invalid Rover move (" + roverMove + ") - Ignoring...");
            }
        }


    }

    protected static void tryRoverMove(final Rover rover) {
        switch (rover.getDirection()) {
            case 'N':
                if (rover.getCurrentY() < rover.getMaxY()) {
                    rover.setCurrentY(rover.getCurrentY() + 1);
                }
                break;
            case 'W':
                if (rover.getCurrentX() > 0) {
                    rover.setCurrentX(rover.getCurrentX() - 1);
                }
                break;
            case 'S':
                if (rover.getCurrentY() > 0) {
                    rover.setCurrentY(rover.getCurrentY() - 1);
                }
                break;
            case 'E':
                if (rover.getCurrentX() < rover.getMaxX()) {
                    rover.setCurrentX(rover.getCurrentX() + 1);
                }
                break;
            default:
                System.out.println("Rover direction is invalid (" + rover.getDirection() + ").  Exiting...");
                System.exit(3);
        }
    }

    protected static void turnRoverRight(final Rover rover) {
        switch (rover.getDirection()) {
            case 'N':
                rover.setDirection('E');
                break;
            case 'E':
                rover.setDirection('S');
                break;
            case 'S':
                rover.setDirection('W');
                break;
            case 'W':
                rover.setDirection('N');
                break;
            default:
                System.out.println("Rover direction is invalid.  Exiting...");
                System.exit(3);
        }
    }

    protected static void turnRoverLeft(final Rover rover) {
        switch (rover.getDirection()) {
            case 'N':
                rover.setDirection('W');
                break;
            case 'W':
                rover.setDirection('S');
                break;
            case 'S':
                rover.setDirection('E');
                break;
            case 'E':
                rover.setDirection('N');
                break;
            default:
                System.out.println("Rover direction is invalid.  Exiting...");
                System.exit(3);
        }
    }

    protected static void checkAndSetRoverStart(final String roverStart, final Rover rover) throws Exception {
        /* parse the grid size and validate */
        if (!"q".equalsIgnoreCase(roverStart)) {
            final String[] roverCoords = roverStart.split(" ");
            if (isInteger(roverCoords[0]) && isInteger(roverCoords[1]) && isValidDirection(roverCoords[2])) {
                rover.setCurrentX(Integer.parseInt(roverCoords[0]));
                rover.setCurrentY(Integer.parseInt(roverCoords[1]));
                rover.setDirection(roverCoords[2].toUpperCase().charAt(0));
            } else {
                throw new Exception("One of the starting position values is invalid.");
            }
        } else {
            System.exit(0);
        }
    }

    protected static boolean isValidDirection(final String roverDirection) throws Exception {
        final String roverDirectionUpperCase = roverDirection.toUpperCase();
        switch (roverDirectionUpperCase) {
            case "N":
            case "S":
            case "E":
            case "W":
                break;
            default:
                throw new Exception ("Direction is invalid, must be (N)orth, (S)outh, (E)ast, or (W)est");
        }
        return true;
    }

    protected static void checkForInvalidGrid(final String[] grid, final Rover rover) throws Exception {
        if (isInteger(grid[0]) && isInteger(grid[1])) {
            rover.setMaxX(Integer.parseInt(grid[0]));
            rover.setMaxY(Integer.parseInt(grid[1]));
        } else {
            final StringBuilder errMsg = new StringBuilder();
            errMsg.append("Invalid grid size detected - X: ")
                    .append(grid[0])
                    .append(", Y: ")
                    .append(grid[1]);
            throw new Exception (errMsg.toString());
        }
    }

    protected static boolean isInteger(final String candidate) {
        boolean result = true;
        try {
            Integer.parseInt(candidate);
        } catch (final NumberFormatException nfe) {
            result = false;
        }

        return result;
    }
}
