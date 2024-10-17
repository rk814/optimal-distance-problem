package furczak.validators;

public class RoutePointsGeneratorValidator {

    public void pointsValidator(int startPoint, int endPoint) {
        if (startPoint < 0) {
            throw new IllegalArgumentException("Start point must be equal or greater than 0");
        }
        if (endPoint < 2) {
            throw new IllegalArgumentException("End point must be greater than 1");
        }
        if (endPoint - startPoint < 2) {
            throw new IllegalArgumentException("End point must be greater start point at least by 2");
        }
    }
}