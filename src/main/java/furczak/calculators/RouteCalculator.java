package furczak.calculators;

import furczak.model.Route;

import java.util.List;

public interface RouteCalculator {
    List<Route> calculateRoutes(List<Integer> availablePoints, int minDist, int maxDist);
}
