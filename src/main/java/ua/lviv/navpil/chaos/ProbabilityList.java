package ua.lviv.navpil.chaos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProbabilityList<T> {

    private double total;
    private final List<ProbabilityWithElement<T>> probabilities = new ArrayList<>();
    private T defaultValue;

    public ProbabilityList() {
    }
    public ProbabilityList(List<T> elements) {
        for (T t : elements) {
            add(t);
        }
    }

    public ProbabilityList<T> add(T t) {
        return this.add(t, 1);
    }
    public ProbabilityList<T> add(T t, double weight) {
        probabilities.add(new ProbabilityWithElement<T>(t, weight));
        total += weight;
        return this;
    }

    public T getForProbability(double probability) {
        if (probabilities.isEmpty()) {
            return defaultValue;
        }
        double v = total * probability;
        double runningTotal = 0;
        for (ProbabilityWithElement<T> p : probabilities) {
            if (runningTotal + p.weight >= v) {
                return p.element;
            } else {
                runningTotal += p.weight;
            }
        }
        return probabilities.get(probabilities.size() - 1).element;
    }

    public ProbabilityList<T> withDefault(T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    private static final class ProbabilityWithElement<El> {
        final El element;
        final double weight;

        private ProbabilityWithElement(El element, double weight) {
            this.element = element;
            this.weight = weight;
        }
    }

}
