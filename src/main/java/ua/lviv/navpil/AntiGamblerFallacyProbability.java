package ua.lviv.navpil;

import java.util.Random;
import java.util.function.Function;

/**
 * Gambler fallacy: if you throw TAILS then HEADS are more probable next throw.
 * This is false.
 * But how would a system behave is throwing TAILS increases a probability to throw HEADS next time?
 * Something like a gamer fallacy in reverse?
 * Not all things are coins and in real life if something happens, then probability of it increases, not decreases.
 * Wars are an example.
 * If two countries are at peace for 200 years, then the war is less likely than if they finished last war 5 years ago.
 */
public class AntiGamblerFallacyProbability {

    public static void main(String[] args) {
        int iterations = 10000;

        Random random = new Random();
        ConfigurableNextProbability np = new ConfigurableNextProbability(0.05, 0.95, .5, (d) -> d * 2, (d) -> d - 0.13);
//        ConfigurableNextProbability np = new ConfigurableNextProbability(0.05, 0.95, .5, (d) -> d * 2, (d) -> d / 2);
        for (int i = 0; i < iterations; i++) {

            double v = random.nextDouble();

            if (v < np.probability) {
                System.out.print("#");
                np.higher();
            } else {
                System.out.print(".");
                np.lower();
            }
        }

    }

    static class ConfigurableNextProbability {

        private final double maxProbablity;
        private final double minProbability;

        private final Function<Double, Double> increaseProbability;
        private final Function<Double, Double> decreaseProbability;

        private double probability = 0.95;


        public ConfigurableNextProbability(
                double minProbability,
                double maxProbablity,
                double initialProbability,
                Function<Double, Double> increaseProbability,
                Function<Double, Double> decreaseProbability) {
            this.maxProbablity = maxProbablity;
            this.minProbability = minProbability;
            this.probability = initialProbability;
            this.increaseProbability = increaseProbability;
            this.decreaseProbability = decreaseProbability;
        }

        public double lower() {
            probability = Math.max(decreaseProbability.apply(probability), minProbability);
            return probability;
        }

        public double higher() {
            probability = Math.min(increaseProbability.apply(probability), maxProbablity);
            return probability;
        }

        public double getProbability() {
            return probability;
        }
    }

}
