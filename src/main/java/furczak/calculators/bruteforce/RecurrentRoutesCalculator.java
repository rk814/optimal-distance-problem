package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RecurrentRoutesCalculator implements RouteCalculator {

    /**
     * Calculates routes with recurrent method.
     * <p>
     * Calculations are initialised with list of list with 0 integer value.
     * This list is updated during each pass of the method and returned as the final result.
     * The initialization and recursive logic are handled by the private method `calculateRoutesWithRecurrence()`.
     * </p>
     * @param availablePoints sorted integer list of available points on route.
     * @param minDist required minimum distance between next points
     * @param maxDist required maximum distance between next points
     * @return list with lists of integer points representing calculated routes
     */
    public List<List<Integer>> calculateRoutes(List<Integer> availablePoints, int minDist, int maxDist) {
        List<Integer> innerList = List.of(0);
        return calculateRoutesWithRecurrence(new ArrayList<>(List.of(innerList)), availablePoints, minDist, maxDist);
    }

    private List<List<Integer>> calculateRoutesWithRecurrence(List<List<Integer>> routes, List<Integer> availablePoints, int minDist, int maxDist) {
        List<List<Integer>> result = new ArrayList<>();

        routes.forEach(route -> {
            int lastRoutePoint = route.get(route.size() - 1);

            if (isRouteAtTheEndPoint(availablePoints, lastRoutePoint)) {
                result.add(route);
            } else {
                List<List<Integer>> updatedList = availablePoints.stream()
                        .filter(point -> point >= lastRoutePoint + minDist && point <= lastRoutePoint + maxDist)
                        .map(point -> Stream.concat(route.stream(), Stream.of(point)).toList())
                        .toList();
                result.addAll(calculateRoutesWithRecurrence(updatedList, availablePoints, minDist, maxDist));
            }
        });

        return result;
    }

    private boolean isRouteAtTheEndPoint(List<Integer> availablePoints, int lastPoint) {
        return lastPoint == availablePoints.get(availablePoints.size() - 1);
    }
}
