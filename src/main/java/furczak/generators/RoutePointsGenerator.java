package furczak.generators;

import lombok.Getter;

import java.util.*;

public class RoutePointsGenerator {

    private final Random random = new Random();

    @Getter
    private final Map<Integer, List<Integer>> sampleLists = new HashMap<>(Map.of(
            1, List.of(0, 43, 59, 64, 77, 90, 110, 115, 124, 142, 161, 183)
    ));


    public List<Integer> getSampleRoutePoints(int key) {
        if (!sampleLists.containsKey(key)) {
            Set<Integer> options = sampleLists.keySet();
            throw new NoSuchElementException(String.format("Available options for sample route points generator are: %s", options));
        }
        return sampleLists.get(key);
    }

    public void addSampleRoutPoints(int key, List<Integer> sample) {
        this.sampleLists.put(key, sample);
    }

    /**
     * Generates a random sequence of route points between the start point (0) and the specified end point.
     *
     * @param numberOfPoints the number of random intermediate points to generate between the start point and end point.
     *                       If this value is 0 or less, no random points will be added.
     * @param endPoint the destination point, which must be greater than 1.
     * @return a list of points starting with 0, containing random intermediate points (if any), and ending with {@code endPoint}.
     *         The list is sorted in ascending order. Points can have duplicates.
     * @throws IllegalArgumentException if {@code endPoint} is less than or equal to 1.
     */
    public List<Integer> generateRandomRoutePoints(int numberOfPoints, int endPoint) {
        endPointValidator(endPoint);
        List<Integer> randomRoutePoints = new ArrayList<>();

        randomRoutePoints.add(0);
        for (int i = 0; i < numberOfPoints; i++) {
            randomRoutePoints.add(random.nextInt(1, endPoint));
        }
        randomRoutePoints.add(endPoint);

        return randomRoutePoints.stream().sorted().toList();
    }

    private void endPointValidator(int endPoint) {
        if (endPoint <= 1) {
            throw new IllegalArgumentException("End point must be greater than 1");
        }
    }
}