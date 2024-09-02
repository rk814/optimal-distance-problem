package furczak.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Slf4j
public class Route implements Comparable<Route> {
    @Getter
    private final List<Integer> route;

    private final List<Integer> routeDistances;

    @Getter
    private final double standardDeviation;


    public Route(List<Integer> route, int perfectDistance) {
        this.route = route;
        this.routeDistances = calculateRouteDistances();
        this.standardDeviation = calculateStandardDeviation(perfectDistance);
    }


    @Override
    public String toString() {
        return "Route: " + route +
                " has " + routeDistances.size() +
                " legs: " + routeDistances +
                ", standard deviation = " + Math.floor(standardDeviation * 10) / 10;
    }

    @Override
    public int compareTo(Route route) {
        return Double.compare(this.standardDeviation, route.getStandardDeviation());
    }

    private List<Integer> calculateRouteDistances() {
        return IntStream.range(1, route.size())
                .mapToObj(index -> route.get(index) - route.get(index - 1))
                .toList();
    }

    private double calculateStandardDeviation(int perfectDistance) {
        log.trace("Started calculateStandardDeviation method...");
        log.debug("Etap distances: {}", routeDistances);
        int etapCount = routeDistances.size();
        log.debug("Etap count: {}", etapCount);
        log.debug("Perfect distance: {}", perfectDistance);

        double sumOfSquaredDifferences = routeDistances.stream()
                .mapToDouble(d -> d - perfectDistance)
                .map(d -> d * d)
                .reduce(Double::sum).orElseThrow(() ->
                        new NoSuchElementException("Empty sequences list"));
        log.debug("Sum of difference between distance and perfect distance squared: {}", sumOfSquaredDifferences);

        double standardDeviation = Math.pow(sumOfSquaredDifferences / etapCount, 0.5);
        log.info("Standard deviation of sequence: {} is {}", route, standardDeviation);
        return standardDeviation;
    }
}
