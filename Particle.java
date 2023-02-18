package Lab2;

import java.util.ArrayList;
import java.util.Random;

public class Particle {
    private int xPos;
    private int yPos;
    private static int xMax;
    private static int yMax;
    Random rand = new Random();
    private static final ArrayList<Particle> particleSet = new ArrayList<>();
    private int dX;
    private int dY;

    // Store the Directions in enum type
    public enum Direction {NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST}

    // Initialize a particle with random position
    public Particle() {
        xPos = rand.nextInt(xMax + 1);
        yPos = rand.nextInt(yMax + 1);
    }

    // Set the boundaries for the Box
    public static void setBound(Box box) {
        xMax = box.getWidth();
        yMax = box.getHeight();
    }
    // Methods to get the random movement
    public Direction generateDirection() {
        Direction[] values = Direction.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }
    // Movement methods with degree of change
    public void move(Direction inputDirect) {
        switch (inputDirect) {
            case NORTH:
                dY = -1;
                break;
            case NORTHEAST:
                dY = -1;
                dX = 1;
                break;
            case EAST:
                dX = 1;
                break;
            case SOUTHEAST:
                dY = 1;
                dX = 1;
                break;
            case SOUTH:
                dY = 1;
                break;
            case SOUTHWEST:
                dY = 1;
                dX = -1;
                break;
            case WEST:
                dX = -1;
                break;
            case NORTHWEST:
                dY = -1;
                dX = -1;
                break;
        }
        this.xPos += dX;
        this.yPos += dY;
    }

    // Set the bounce back effect if touched the boundaries
    public void isBounced() {
        if (xPos < 0 || xPos > xMax) {
            dX *= -1;
            this.xPos += dX * 2;
        }
        if (yPos < 0 || yPos > yMax) {
            dY *= -1;
            this.yPos += dY * 2;
        }
    }

    // Check if two particles are in collision and add new particles
    public void isCollided(Particle target) {
        int thisXPost = this.xPos + dX;
        int thisYPost = this.yPos + dY;
        int targetXPost = target.xPos - dX;
        int targetYPost = target.yPos - dY;
        if (this.xPos == target.xPos && this.yPos == target.yPos) {
            if (thisXPost < 0 || thisXPost > xMax || thisYPost < 0 || thisYPost > yMax) {
                this.xPos = thisXPost;
                this.yPos = thisYPost;
            } else {
                this.xPos -= dX;
                this.yPos -= dY;
            }
            if (targetXPost < 0 || targetXPost > xMax || targetYPost < 0 || targetYPost > yMax) {
                target.xPos = targetXPost;
                target.yPos = targetYPost;
            } else {
                target.xPos += dX;
                target.yPos += dY;
            }
            Particle newParticle = new Particle();
            particleSet.add(newParticle);
        }
    }

    // Initialize new array of 3 particle at the beginning
    public static void initialize() {
           for (int i = 1; i < 4; i++) {
               Particle newParticle = new Particle();
               particleSet.add(newParticle);
           }
    }

    public static ArrayList<Particle> getParticleSet() {
        return particleSet;
    }

    public int getXPos() {
        return this.xPos;
    }


    public int getYPos() {
        return this.yPos;
    }
}