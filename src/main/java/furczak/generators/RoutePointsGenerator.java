package furczak.generators;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class RoutePointsGenerator {

    private final Random random = new Random();

    public List<Integer> getSampleRoutePoints() {
        return List.of(0, 12, 23, 35, 42, 51, 66, 70,85, 100);
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