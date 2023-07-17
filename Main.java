

package Lab2;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        int numberOfTrials;
        while (1==1) {
            System.out.println("test");
        }
        if (args.length > 0) {
        // Receive input for the iterations
            numberOfTrials = Integer.parseInt(args[0]);
        } else {
            Scanner sc = new Scanner(System.in);
        // Receive input for the iterations
            numberOfTrials = sc.nextInt();
            System.out.println("Type the iteration(s) you want to do:");
      }
        Scanner sc = new Scanner(System.in);

        // Initialize a new Box with given height and width
        Box myBox = Box.getInstance(10, 20);
        Particle.setBound(myBox);


        // Create the border and initialize first 3 particles
        myBox.createBorder();
        Particle.initialize();

        // Print out the initial phase
        System.out.println("First trial:");
        myBox.drawBox(Particle.getParticleSet());
        System.out.println("Number of particles: " + Particle.getParticleSet().size());

        // Simulation
        Simulation.simulationBox(myBox, numberOfTrials);
    }
}
