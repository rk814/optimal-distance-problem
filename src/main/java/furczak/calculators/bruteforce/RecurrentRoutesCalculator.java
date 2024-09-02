package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RecurrentRoutesCalculator implements RouteCalculator {

    public List<List<Integer>> calculateRoutes(List<Integer> availablePoints, int minDist, int maxDist) {
        List<Integer> innerList = List.of(0);
        return calculateRoutes(new ArrayList<>(List.of(innerList)), availablePoints, minDist, maxDist);
    }

    private List<List<Integer>> calculateRoutes(List<List<Integer>> routes, List<Integer> availablePoints, int minDist, int maxDist) {
        List<List<Integer>> result = new ArrayList<>();

        routes.forEach(r -> {
            int lastPoint = r.get(r.size() - 1);

            if (lastPoint == availablePoints.get(availablePoints.size() - 1)) {
                result.add(r);
            } else {
                List<List<Integer>> updatedList = availablePoints.stream()
                        .filter(p -> p >= lastPoint + minDist && p <= lastPoint + maxDist)
                        .map(p -> Stream.concat(r.stream(), Stream.of(p)).toList())
                        .toList();
                result.addAll(calculateRoutes(updatedList, availablePoints, minDist, maxDist));
            }
        });

        return result;
    }
}
