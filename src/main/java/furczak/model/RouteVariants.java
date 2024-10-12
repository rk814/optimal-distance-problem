package furczak.model;

import furczak.calculators.RouteCalculator;
import lombok.Getter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Getter
public class RouteVariants implements BestRouteFinder {

    private final RouteCalculator routeCalculator;

    private int minDistance = 30;
    private int maxDistance = 50;

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


    @Override
    public void calculate() {
        if (availablePoints==null) {
            throw new NullPointerException("Set up available route points first");
        }
        this.routes = routeCalculator.calculateRoutes(availablePoints, minDistance, maxDistance);
    }

    @Override
    public Route getBestRoute() {
        List<Double> sequencesDeviation = routes.stream().map(Route::getStandardDeviation).toList();
        double bestSequenceDeviation = sequencesDeviation.stream().min(Double::compareTo)
                .orElseThrow(() -> new NoSuchElementException("Empty sequences list"));
        return routes.get(sequencesDeviation.indexOf(bestSequenceDeviation));
    }

    public List<Route> getRoutes() {
        if (routes == null) {
            throw new NullPointerException("First calculate routes with calculateRouteVariants method");
        }
        return routes;
    }

    public void setDistances(int minDistance, int maxDistance) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.routes = null;
    }

    public void setAvailablePoints(List<Integer> availablePoints) {
        this.availablePoints = availablePoints;
        this.routes = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Route contains ").append(routes.size()).append(" variant(s), the distance is set up between ").append(minDistance)
                .append(" and ").append(maxDistance).append(", the average perfect distance is ").append(getPerfectDistance())
                .append(", calculated with ").append(getSimpleCalculatorName()).append("\n");

        for (int i = 0; i < routes.size(); i++) {
            sb.append(i + 1).append(". ").append(routes.get(i).toString()).append("\n");
        }

        return sb.toString();
    }

    private String getSimpleCalculatorName() {
        String[] split = routeCalculator.getClass().toString().split("\\.");
        return split[split.length-1];
    }

    private int getPerfectDistance() {
        return (minDistance + maxDistance) / 2;
    }
}
