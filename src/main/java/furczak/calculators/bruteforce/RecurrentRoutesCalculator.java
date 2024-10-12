package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;
import furczak.model.Route;

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
    public List<Route> calculateRoutes(List<Integer> availablePoints, int minDist, int maxDist) {
        List<Route> innerList = new ArrayList<>();
        innerList.add(new Route(List.of(0), minDist, maxDist));
        return calculateRoutesWithRecurrence(innerList, availablePoints, minDist, maxDist);
    }

    private List<Route> calculateRoutesWithRecurrence(List<Route> routes, List<Integer> availablePoints, int minDist, int maxDist) {
        List<Route> result = new ArrayList<>();

        routes.forEach(route -> {
            int lastRoutePoint = route.getLastRoutePoint();

            if (isRouteAtTheEndPoint(availablePoints, lastRoutePoint)) {
                result.add(route);
            } else {
                List<Route> updatedList = availablePoints.stream()
                        .filter(point -> point >= lastRoutePoint + minDist && point <= lastRoutePoint + maxDist)
                        .map(point -> Stream.concat(route.getRoute().stream(), Stream.of(point)).toList())
                        .map(list-> new Route(list, minDist, maxDist))
                        .toList();
                result.addAll(calculateRoutesWithRecurrence(updatedList, availablePoints, minDist, maxDist));
            }
        });

        return result;
    }

    public boolean isRouteAtTheEndPoint(List<Integer> availablePoints, int lastPoint) {
        return lastPoint == availablePoints.get(availablePoints.size() - 1);
    }
}
