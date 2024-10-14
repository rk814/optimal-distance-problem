package furczak.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Slf4j
public class StageRoute implements Comparable<StageRoute> {
    @Getter
    private final List<Integer> route;

    private List<Integer> routeDistances;

    @Getter
    private Double standardDeviation;


    private final RouteVariants routeVariants;


    public StageRoute(List<Integer> route, RouteVariants routeVariants) {
        this.route = route;
        this.routeVariants = routeVariants;
        if (isRouteComplete()) {
            log.info("Route {} was completed", route);
            this.routeDistances = calculateRouteDistances();
            this.standardDeviation = calculateStandardDeviation();
        }
    }


    public int getLastRoutePoint() {
        return route.get(route.size() - 1);
    }

    public boolean isRouteComplete() {
        List<Integer> availablePoints = routeVariants.getAvailablePoints();
        int lastAvailablePoint = availablePoints.get(availablePoints.size() - 1);
        return lastAvailablePoint == getLastRoutePoint();
    }

    @Override
    public String toString() {
        if (route==null) {
            return "Route is empty";
        }

        return "Route: " + route +
                " has " + routeDistances.size() +
                " legs: " + routeDistances +
                ", standard deviation = " + Math.floor(standardDeviation * 10) / 10;
    }

    @Override
    public int compareTo(StageRoute stageRoute) {
        return Double.compare(this.standardDeviation, stageRoute.getStandardDeviation());
    }


    private List<Integer> calculateRouteDistances() {
        List<Integer> distances = IntStream.range(1, route.size())
                .mapToObj(index -> route.get(index) - route.get(index - 1))
                .toList();
        log.debug("Calculated route distances for route: {} are {}", route, distances);
        return distances;
    }

    private double calculateStandardDeviation() {
        log.trace("Started calculateStandardDeviation method...");
        int etapCount = routeDistances.size();
        int perfectDistance = routeVariants.getDivisionBoundaries().getPerfectDistance();

        double sumOfSquaredDifferences = routeDistances.stream()
                .mapToDouble(d -> d - perfectDistance)
                .map(d -> d * d)
                .reduce(Double::sum).orElseThrow(() ->
                        new NoSuchElementException("Empty sequences list"));

        double standardDeviation = Math.pow(sumOfSquaredDifferences / etapCount, 0.5);
        log.debug("Calculated standard deviation for route: {} is {}", route, standardDeviation);
        return standardDeviation;
    }
}
