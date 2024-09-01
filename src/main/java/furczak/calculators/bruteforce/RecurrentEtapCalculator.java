package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@Slf4j
@Getter
public class RecurrentEtapCalculator implements RouteCalculate {
    private int minDistance = 30;
    private int maxDistance = 50;

    private final List<Integer> routePoints;


    public RecurrentEtapCalculator(List<Integer> routePoints) {
        this.routePoints = routePoints;
    }

    public RecurrentEtapCalculator(int minDistance, int maxDistance, List<Integer> routePoints) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.routePoints = routePoints;
    }


    public List<List<Integer>> calculateRoutes() {
        List<Integer> innerList = List.of(0);
        return calculateRoutes(new ArrayList<>(List.of(innerList)));
    }

    public List<List<Integer>> calculateRoutes(List<List<Integer>> routes) {
        List<List<Integer>> result = new ArrayList<>();

        routes.forEach(r -> {
            int lastPoint = r.get(r.size() - 1);

            if (lastPoint == routePoints.get(routePoints.size() - 1)) {
                result.add(r);
            } else {
                List<List<Integer>> updatedList = routePoints.stream()
                        .filter(p -> p >= lastPoint + minDistance && p <= lastPoint + maxDistance)
                        .map(p -> Stream.concat(r.stream(), Stream.of(p)).toList())
                        .toList();
                result.addAll(calculateRoutes(updatedList));
            }
        });

        return result;
    }

    public List<Integer> getBestSequence(List<List<Integer>> sequences) {
        List<Double> sequencesDeviation = sequences.stream().map(this::getStandardDeviation).toList();
        // TODO improve comparator
        double bestSequenceDeviation = sequencesDeviation.stream().min(Double::compareTo)
                .orElseThrow(() -> new NoSuchElementException("Empty sequences list"));
        return sequences.get(sequencesDeviation.indexOf(bestSequenceDeviation));
    }

    private double getStandardDeviation(List<Integer> sequence) {
        log.trace("Started getStandardDeviation method...");
        List<Integer> etapDistances = IntStream.range(1, sequence.size())
                .mapToObj(index -> sequence.get(index) - sequence.get(index - 1))
                .toList();
        log.debug("Etap distances: {}", etapDistances);
        int etapCount = etapDistances.size();
        log.debug("Etap count: {}", etapCount);
        int perfectDistance = (minDistance + maxDistance) / 2;
        log.debug("Perfect distance: {}", perfectDistance);

        double sumOfSquaredDifferences = etapDistances.stream()
                .mapToDouble(d -> d - perfectDistance)
                .map(d -> d * d)
                .reduce(Double::sum).orElseThrow(() ->
                        new NoSuchElementException("Empty sequences list"));
        log.debug("Sum of difference between distance and perfect distance squared: {}", sumOfSquaredDifferences);

        double standardDeviation = Math.pow(sumOfSquaredDifferences / etapCount, 0.5);
        log.info("Standard deviation of sequence: {} is {}", sequence, standardDeviation);
        return standardDeviation;
    }
}
