package furczak.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Slf4j
@Getter
public class Route implements Comparable<Route>{
    private final int minDistance;

    private final int maxDistance;

    private final List<Integer> routePoints;

    private final List<Integer> stageDistances;

    private final double standardDeviation;


    public Route(int minDistance, int maxDistance, List<Integer> route) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.routePoints = route;
        this.standardDeviation = calculateStandardDeviation();
        this.stageDistances = calculateStageDistances();
    }


    @Override
    public String toString() {
        return "Route: " + routePoints +
                "has " + stageDistances.size() +
                "stages: " + stageDistances +
                ", standard deviation=" + standardDeviation +
                ", perfect stage=" + getPerfectStage();
    }

    @Override
    public int compareTo(Route route) {
        return Double.compare(this.standardDeviation, route.getStandardDeviation());
    }

    private int getPerfectStage() {
        return (minDistance + maxDistance) / 2;
    }

    private double calculateStandardDeviation() {
        log.trace("Started calculateStandardDeviation method...");
        log.debug("Etap distances: {}", stageDistances);
        int etapCount = stageDistances.size();
        log.debug("Etap count: {}", etapCount);
        int perfectDistance = getPerfectStage();
        log.debug("Perfect distance: {}", perfectDistance);

        double sumOfSquaredDifferences = stageDistances.stream()
                .mapToDouble(d -> d - perfectDistance)
                .map(d -> d * d)
                .reduce(Double::sum).orElseThrow(() ->
                        new NoSuchElementException("Empty sequences list"));
        log.debug("Sum of difference between distance and perfect distance squared: {}", sumOfSquaredDifferences);

        double standardDeviation = Math.pow(sumOfSquaredDifferences / etapCount, 0.5);
        log.info("Standard deviation of sequence: {} is {}", routePoints, standardDeviation);
        return standardDeviation;
    }

    private List<Integer> calculateStageDistances() {
        return IntStream.range(1, routePoints.size())
                .mapToObj(index -> routePoints.get(index) - routePoints.get(index - 1))
                .toList();
    }
}
