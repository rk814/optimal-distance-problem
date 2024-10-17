package furczak.generators;

import lombok.Getter;

import java.util.*;

public class RoutePointsGenerator {

    private final Random random = new Random();

    @Getter
    private final Map<Integer, List<Integer>> sampleLists = new HashMap<>(Map.of(
            1, List.of(0, 43, 59, 64, 77, 90, 110, 115, 124, 142, 161, 183),
            2, List.of(0, 3, 9, 13, 33, 34, 34, 44, 48, 57, 64, 66, 70, 75, 76, 76, 79, 98, 102, 122, 124, 135, 143, 149, 150, 151, 157, 161, 162, 162, 163, 163, 180, 188, 190, 190, 205, 207, 213, 227, 229, 230, 233, 244, 249, 250, 254, 255, 256, 269, 291, 300),
            3, List.of(0, 9, 12, 13, 18, 35, 37, 40, 40, 51, 52, 60, 64, 72, 73, 73, 75, 81, 87, 98, 117, 136, 158, 164, 172, 175, 194, 195, 198, 209, 222, 224, 225, 235, 245, 266, 268, 292, 294, 304, 314, 321, 322, 326, 336, 339, 347, 354, 359, 360, 397, 400)
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