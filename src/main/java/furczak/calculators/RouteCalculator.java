package furczak.calculators;

import java.util.List;

public interface RouteCalculator {
    List<List<Integer>> calculateRoutes(List<Integer> availablePoints, int minDist, int maxDist);
}
