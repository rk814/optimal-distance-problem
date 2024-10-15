package furczak.validators;

import java.util.List;

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
        if (availablePoints.isEmpty()) {
            throw new IllegalArgumentException("List of available points is empty");
        }
        if (availablePoints.stream().anyMatch(point -> point <= 0)) {
            throw new IllegalArgumentException("List of available points cannot contain values less or equal to 0");
        }
    }
}
