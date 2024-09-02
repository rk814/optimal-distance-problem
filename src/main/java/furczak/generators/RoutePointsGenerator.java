package furczak.generators;

import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class RoutePointsGenerator {

    private final Random random = new Random();

    private final Map<Integer, List<Integer>> simpleLists = Map.of(
            1, List.of(0, 43, 59, 64, 77, 90, 110, 115, 124, 142, 161, 183)
    );


    public List<Integer> getSampleRoutePoints(int number) {
        if (!simpleLists.containsKey(number)) {
            Set<Integer> options = simpleLists.keySet();
            throw new NoSuchElementException(String.format("Available options for sample route points generator are: %s", options));
        }
        return simpleLists.get(number);
    }

    public List<Integer> generateRandomRoutePoints(int numberOfPoints, int endPoint) {
        List<Integer> randomRoutePoints = new ArrayList<>();
        for (int i = 0; i < numberOfPoints; i++) {
            randomRoutePoints.add(random.nextInt(1, endPoint));
        }
        randomRoutePoints.add(0);
        randomRoutePoints.add(endPoint);
        return randomRoutePoints.stream().sorted().toList();
    }
}