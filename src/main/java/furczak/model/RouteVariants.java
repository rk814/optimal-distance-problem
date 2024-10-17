package furczak.model;

import furczak.calculators.RouteCalculator;
import furczak.comparators.StandardDeviationComparator;
import furczak.validators.RouteVariantsValidator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NoSuchElementException;

@Getter
@Slf4j
public class RouteVariants implements BestRouteFinder {

    private final RouteVariantsValidator validator = new RouteVariantsValidator();

    private final RouteCalculator routeCalculator;

    private DistanceBoundaries distanceBoundaries;

    private List<Integer> availablePoints;

    private List<StageRoute> calculatedStageRoutes;


    /**
     * Constructs a {@code RouteVariants} instance.
     * <p>
     * After instantiating this class, the available points must be set using {@link #setAvailablePoints(List)}.
     * The list of available points must contain at least 2 points.
     * Once the points are set, the route calculations should be initiated by calling the {@link #calculate()} method.
     * </p>
     *
     * @param routeCalculator an instance of the preferred type of {@code RouteCalculator}
     * @param minDistance the minimum allowable distance (in days) for route calculations
     * @param maxDistance the maximum allowable distance (in days) for route calculations
     */
    public RouteVariants(RouteCalculator routeCalculator, int minDistance, int maxDistance) {
        this.routeCalculator = routeCalculator;
        routeCalculator.setRouteVariants(this);
        this.distanceBoundaries = new DistanceBoundaries(minDistance, maxDistance);
    }

    public RouteVariants(RouteCalculator routeCalculator) {
        this.routeCalculator = routeCalculator;
    }

    /**
     * Calculates all available routes based on the current input data.
     * <p>
     *     This method finds all possible routes using the available points and distance boundaries,
     *     then calculates route parameters such as lag (day) distances and standard deviation for each route.
     * </p>
     *
     * @throws IllegalStateException if the available points or distance boundaries are not set up properly.
     */
    @Override
    public void calculate() {
        log.trace("Started calculate() method...");
        if (availablePoints == null || distanceBoundaries == null) {
            throw new NullPointerException("Set up available route points and distance boundaries first");
        }
        List<StageRoute> calculatedStageRoutes = routeCalculator.calculateRoutes();
        calculatedStageRoutes.forEach(StageRoute::calculate);
        calculatedStageRoutes.sort(new StandardDeviationComparator());
        this.calculatedStageRoutes = calculatedStageRoutes;
    }


    /**
     * Retrieves the best route based on the lowest standard deviation.
     *
     * @return the best {@code StageRoute}.
     */
    @Override
    public StageRoute getBestRoute() {
        return calculatedStageRoutes.stream().min(StageRoute::compareTo)
                .orElseThrow(() -> new NoSuchElementException("Empty sequences list"));
    }

    public List<StageRoute> getCalculatedStageRoutes() {
        if (calculatedStageRoutes == null) {
            throw new NullPointerException("First calculate routes with calculateRouteVariants method");
        }
        return calculatedStageRoutes;
    }

    public int getLastAvailablePoint() {
        return availablePoints.get(availablePoints.size() - 1);
    }

    public int getFirstAvailablePoint() {
        return availablePoints.get(0);
    }

    /**
     * Sets the available points for route calculations.
     * <p>
     * Setting new points will reset any previously calculated results.
     * </p>
     * @param availablePoints the list of Integer points representing possible lag (day) breaks on the route.
     *      The list must contain at least 2 points in ascending order.
     * @throws IllegalArgumentException if point list is empty or contains fewer than 2 values, or if any value is negative.
     */
    public void setAvailablePoints(List<Integer> availablePoints) {
        validator.validateAvailablePoints(availablePoints);
        this.availablePoints = availablePoints;
        this.calculatedStageRoutes = null;
    }

    /**
     * Sets the minimum and maximum distances for route calculations.
     * <p>
     * Setting new values will reset any previously calculated results.
     * </p>
     * @param min the minimum allowable distance (in days) for route calculations
     * @param max the maximum allowable distance (in days) for route calculations
     * @throws IllegalArgumentException if arguments are less than 0 or min is greater than max.
     */
    public void setDistances(int min, int max) {
        validator.validateDistances(min, max);
        this.distanceBoundaries = new DistanceBoundaries(min, max);
        this.calculatedStageRoutes = null;
    }

    @Override
    public String toString() {
        if (calculatedStageRoutes == null || distanceBoundaries == null) {
            return "Calculations was not started jet";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Route contains ").append(calculatedStageRoutes.size()).append(" variant(s), the distance is set up between ")
                .append(distanceBoundaries.getMin()).append(" and ").append(distanceBoundaries.getMax())
                .append(", the average perfect distance is ").append(distanceBoundaries.getPerfectDistance())
                .append(", calculated with ").append(routeCalculator.getName()).append("\n\n");

        for (int i = 0; i < calculatedStageRoutes.size(); i++) {
            sb.append(i + 1).append(". ").append(calculatedStageRoutes.get(i).toString()).append("\n");
        }

        return sb.toString();
    }
}
