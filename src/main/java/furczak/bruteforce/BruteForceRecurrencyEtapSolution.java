package furczak.bruteforce;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class BruteForceRecurrencyEtapSolution {
    private int minDistance = 30;
    private int maxDistance = 50;

    private final List<Integer> routePoints;

    public BruteForceRecurrencyEtapSolution(List<Integer> routePoints) {
        this.routePoints = routePoints;
    }

    public BruteForceRecurrencyEtapSolution(int minDistance, int maxDistance, List<Integer> routePoints) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.routePoints = routePoints;
    }

    public List<List<Integer>> calculateRoutes() {
        List<Integer> innerList = List.of(0);
        return calculateRoutes(new ArrayList<>(List.of(innerList)));
    }

    public List<List<Integer>> calculateRoutes(List<List<Integer>> routes) {
        List<List<Integer>> result = new ArrayList<>();

        routes.forEach(r -> {
            int lastPoint = r.get(r.size() - 1);
            List<List<Integer>> updatedList = routePoints.stream()
                    .filter(p -> p > lastPoint + minDistance && p < lastPoint + maxDistance)
                    .map(p -> Stream.concat(r.stream(), Stream.of(p)).toList())
                    .toList();

            if (updatedList.isEmpty()) {
                if (lastPoint == routePoints.get(routePoints.size() - 1)) {
                    result.add(r);
                }
            } else {
                result.addAll(calculateRoutes(updatedList));
            }
        });

        return result;
    }

    private List<Integer> buildNewSubList(List<Integer> currentList, Integer point) {
        currentList.add(point);
        return currentList;
    }

    public List<Integer> getBestSequence(List<List<Integer>> sequences) {
        //TODO
        return null;
    }
}
