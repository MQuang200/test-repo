package Lab2;

public class Simulation {
    public static void simulationBox(Box myBox, int numberOfTrials) {
        for (int i = 1; i <= numberOfTrials; i++) {
            if (Particle.getParticleSet().size() <= 100) {
                myBox.createBorder();
                for (int k = 0; k < Particle.getParticleSet().size(); k++) {
                    Particle.Direction way = Particle.getParticleSet().get(k).generateDirection();
                    Particle.getParticleSet().get(k).move(way);
                    Particle.getParticleSet().get(k).isBounced();
                    for (int g = k + 1; g < Particle.getParticleSet().size(); g++) {
                        Particle.getParticleSet().get(k).isCollided(Particle.getParticleSet().get(g));
                    }
                }
                System.out.println("Trials: " + i);
                myBox.drawBox(Particle.getParticleSet());
                System.out.println("Number of particles: " + Particle.getParticleSet().size());
            }
        }
        if (Particle.getParticleSet().size() > 100) {
            System.out.println("Number of particle exceeds 100! Stopping");
        }
    }
}
