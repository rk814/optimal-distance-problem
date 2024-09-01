package furczak.generators;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class RoutePointsGenerator {

    private final Random random = new Random();

    public List<Integer> getSampleRoutePoints() {
        return List.of(0, 43, 59, 64, 77, 90, 110, 115,124, 142, 161, 183);
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