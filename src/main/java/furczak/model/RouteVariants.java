package furczak.model;

import furczak.calculators.RouteCalculator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NoSuchElementException;

@Getter
@Slf4j
public class RouteVariants implements BestRouteFinder {

    private final RouteCalculator routeCalculator;

    private DivisionDistance divisionDistance;

    private List<Integer> availablePoints;

    private List<StageRoute> calculatedStageRoutes;


    public RouteVariants(RouteCalculator routeCalculator) {
        this.routeCalculator = routeCalculator;
    }

    public RouteVariants(RouteCalculator routeCalculator, int minDistance, int maxDistance) {
        this.routeCalculator = routeCalculator;
        routeCalculator.setRouteVariants(this);
        this.divisionDistance = new DivisionDistance(minDistance, maxDistance);
    }


    @Override
    public void calculate() {
        log.trace("Started calculate() method...");
        if (availablePoints == null) {
            throw new NullPointerException("Set up available route points first");
        }
        this.calculatedStageRoutes = routeCalculator.calculateRoutes();
    }

    @Override
    public StageRoute getBestRoute() {
        List<Double> sequencesDeviation = calculatedStageRoutes.stream().map(StageRoute::getStandardDeviation).toList();
        double bestSequenceDeviation = sequencesDeviation.stream().min(Double::compareTo)
                .orElseThrow(() -> new NoSuchElementException("Empty sequences list"));
        return calculatedStageRoutes.get(sequencesDeviation.indexOf(bestSequenceDeviation));
    }

    public List<StageRoute> getCalculatedStageRoutes() {
        if (calculatedStageRoutes == null) {
            throw new NullPointerException("First calculate routes with calculateRouteVariants method");
        }
        return calculatedStageRoutes;
    }

    public void setDistances(int minDistance, int maxDistance) {
        this.divisionDistance = new DivisionDistance(minDistance, maxDistance);
        this.calculatedStageRoutes = null;
    }

    public void setAvailablePoints(List<Integer> availablePoints) {
        this.availablePoints = availablePoints;
        this.calculatedStageRoutes = null;
    }

    @Override
    public String toString() {
        if (calculatedStageRoutes==null || divisionDistance ==null) {
            return "Calculations was not started jet";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Route contains ").append(calculatedStageRoutes.size()).append(" variant(s), the distance is set up between ")
                .append(divisionDistance.getMin()).append(" and ").append(divisionDistance.getMax())
                .append(", the average perfect distance is ").append(divisionDistance.getPerfectDistance())
                .append(", calculated with ").append(routeCalculator.getName()).append("\n\n");

        for (int i = 0; i < calculatedStageRoutes.size(); i++) {
            sb.append(i + 1).append(". ").append(calculatedStageRoutes.get(i).toString()).append("\n");
        }

        return sb.toString();
    }
}
