package furczak.model;

import furczak.calculators.RouteCalculator;
import furczak.comparators.StandardDeviationComparator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NoSuchElementException;

@Getter
@Slf4j
public class RouteVariants implements BestRouteFinder {

    private final RouteCalculator routeCalculator;

    private DistanceBoundaries distanceBoundaries;

    private List<Integer> availablePoints;

    private List<StageRoute> calculatedStageRoutes;


    public RouteVariants(RouteCalculator routeCalculator) {
        this.routeCalculator = routeCalculator;
    }

    public RouteVariants(RouteCalculator routeCalculator, int minDistance, int maxDistance) {
        this.routeCalculator = routeCalculator;
        routeCalculator.setRouteVariants(this);
        this.distanceBoundaries = new DistanceBoundaries(minDistance, maxDistance);
    }


    @Override
    public void calculate() {
        log.trace("Started calculate() method...");
        if (availablePoints == null) {
            throw new NullPointerException("Set up available route points first");
        }
        List<StageRoute> calculatedStageRoutes = routeCalculator.calculateRoutes();
        calculatedStageRoutes.forEach(StageRoute::calculate);
        calculatedStageRoutes.sort(new StandardDeviationComparator());
        this.calculatedStageRoutes = calculatedStageRoutes;
    }

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

    public void setDistances(int minDistance, int maxDistance) {
        this.distanceBoundaries = new DistanceBoundaries(minDistance, maxDistance);
        this.calculatedStageRoutes = null;
    }

    public void setAvailablePoints(List<Integer> availablePoints) {
        this.availablePoints = availablePoints;
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
