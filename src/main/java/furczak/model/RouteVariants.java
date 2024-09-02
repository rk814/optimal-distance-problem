package furczak.model;

import furczak.calculators.RouteCalculator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Getter
public class RouteVariants implements BestRouteFinder {

    private final RouteCalculator routeCalculator;

    private int minDistance = 30;
    private int maxDistance = 50;

    @Setter
    private List<Integer> availablePoints;
    private List<Route> routes;


    public RouteVariants(RouteCalculator routeCalculator) {
        this.routeCalculator = routeCalculator;
    }

    public RouteVariants(RouteCalculator routeCalculator, int minDistance, int maxDistance) {
        this.routeCalculator = routeCalculator;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    public void calculate() {
        List<List<Integer>> routes = routeCalculator.calculateRoutes(availablePoints, minDistance, maxDistance);
        this.routes = routes.stream().map(route -> new Route(route, minDistance, maxDistance)).sorted().collect(Collectors.toList());
    }

    @Override
    public Route getBestRoute() {
        List<Double> sequencesDeviation = routes.stream().map(Route::getStandardDeviation).toList();
        double bestSequenceDeviation = sequencesDeviation.stream().min(Double::compareTo)
                .orElseThrow(() -> new NoSuchElementException("Empty sequences list"));
        return routes.get(sequencesDeviation.indexOf(bestSequenceDeviation));
    }

    public void setDistances(int minDistance, int maxDistance) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    public List<Route> getRoutes() {
        if (routes == null) {
            throw new NullPointerException("First calculate routes with calculateRouteVariants method");
        }
        return routes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Route contains ").append(routes.size()).append(" variants, distance between [").append(minDistance)
                .append(", ").append(maxDistance).append("], calculated with ").append(routeCalculator);

        for (int i = 0; i < routes.size(); i++) {
            sb.append(i + 1).append(". ").append(routes.get(i).toString()).append("\n");
        }

        return sb.toString();
    }
}
