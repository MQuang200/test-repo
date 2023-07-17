package Lab2;

import java.util.ArrayList;

public class Box {
    private final in width;
    private final int height;
    private static Box object = null;
    private String maps[][];

    // Implement Singleton design pattern
    private Box(int inputHeight, int inputWidth) {
        height = inputHeight;
        width = inputWidth;
    }

    // Initialize a new Box object
    public static Box getInstance(int inputHeight, int inputWidth) {
        if (object == null) {
            object = new Box(inputHeight, inputWidth);
        }
        return object;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Using a 2D array to store the box properties
    public void createBorder() {
        maps = new String[height + 1][width + 1];
        for (int i = 0; i <= height ; i++) {
            for (int j = 0; j <= width ; j++) {
                if (i == 0 || i == height) {
                    maps[i][j] = "-";
                } else if (j == 0 || j == width) {
                    maps[i][j] = "|";
                } else {
                    maps[i][j] = " ";
                }
            }
        }
    }

    // Put the particles in and draw the Box object
    public void drawBox(ArrayList<Particle> particles) {
        for (int i = 0; i < particles.size(); i++) {
            for (int j = 0; j <= height; j++) {
                for (int k = 0; k <= width; k++) {
                    if (j == particles.get(i).getYPos() && k == particles.get(i).getXPos()) {
                        maps[j][k] = "*";
                    }
                }
            }
        }
        for (int i = 0; i <= height; i++) {
            for (int j = 0; j <= width; j++) {
                System.out.print(maps[i][j]);
            }
            System.out.print("\n");
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maps[i][j] = " ";
            }
        }
    }
}

