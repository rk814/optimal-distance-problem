package furczak.validators;

import java.util.List;
import java.util.stream.IntStream;

public class RouteVariantsValidator {
    public void validateDistances(int min, int max) {
        if (min <= 0 || max <= 0) {
            throw new IllegalArgumentException("Route distances must be greater then 0");
        }
        if (min > max) {
            throw new IllegalArgumentException("Max value must be greater or equal to min value");
        }
    }

    public void validateAvailablePoints(List<Integer> availablePoints) {
        if (availablePoints == null || availablePoints.size() < 2) {
            throw new IllegalArgumentException("List of available points must contain at least 2 points");
        }
        if (availablePoints.stream().anyMatch(point -> point < 0)) {
            throw new IllegalArgumentException("List of available points cannot contain values less than 0");
        }
        if (!isSorted(availablePoints)) {
            throw new IllegalArgumentException("List of available points must be in ascending order");
        }
    }

    private boolean isSorted(List<Integer> list) {
        return IntStream.range(1, list.size()).allMatch(n -> list.get(n) >= list.get(n - 1));
    }
}
