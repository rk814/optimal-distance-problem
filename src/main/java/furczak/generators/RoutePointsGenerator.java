package furczak.generators;

import furczak.validators.RoutePointsGeneratorValidator;
import java.util.*;


public class RoutePointsGenerator {

    private final RoutePointsGeneratorValidator validator = new RoutePointsGeneratorValidator();

    private final Random random = new Random();


    /**
     * Generates a random sequence of route points between the specified start point and the end point.
     *
     * @param numberOfPoints the number of random intermediate points to generate between the start point and end point.
     *      If this value is 0 or less, no random points will be added.
     * @param startPoint the departure point, which must be equal or greater than 0.
     * @param endPoint the destination point, which must be equal or greater than 2 and also greater than {@code startPoint}
     *      by at least 2.
     * @return a list of points starting with {@code startPoint}, containing random intermediate points (if any),
     *      and ending with {@code endPoint}. The list is sorted in ascending order. Points can have duplicates.
     * @throws IllegalArgumentException if {@code startPoint} is less than 0 or {@code endPoint} is less than 2
     *      or {@code endPoint} is not greater then {@code startPoint} at least by 2.
     */
    public List<Integer> generateRandomRoutePoints(int numberOfPoints, int startPoint, int endPoint) {
        validator.pointsValidator(startPoint, endPoint);
        List<Integer> randomRoutePoints = new ArrayList<>();

        randomRoutePoints.add(startPoint);
        for (int i = 0; i < numberOfPoints; i++) {
            randomRoutePoints.add(random.nextInt(startPoint + 1, endPoint));
        }
        randomRoutePoints.add(endPoint);

        return randomRoutePoints.stream().sorted().toList();
    }
}