package furczak;

import furczak.calculators.bruteforce.IteratorRoutesCalculator;
import furczak.calculators.bruteforce.RecurrentRoutesCalculator;
import furczak.generators.RoutePointsGenerator;
import furczak.model.StageRoute;
import furczak.model.RouteVariants;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // sample setup
        int sampleNumber = 1;
        boolean generate = false;
        int numerOfPoints = 50;
        int startPoint = 0;
        int endPoint = 400;

        // calculator setup
        int minDistance = 22;
        int maxDistance = 44;
        var calculator = new RecurrentRoutesCalculator();


        RoutePointsGenerator generator = new RoutePointsGenerator();
        List<Integer> availablePoints = generator.getSampleRoutePoints(sampleNumber);
        if (generate) {
            availablePoints = generator.generateRandomRoutePoints(numerOfPoints, startPoint, endPoint);
        }
        System.out.printf("Points list: %s%n", availablePoints);

        RouteVariants simpleRouteVariants = new RouteVariants(calculator, minDistance, maxDistance);
        simpleRouteVariants.setAvailablePoints(availablePoints);

        long start = System.currentTimeMillis();
        simpleRouteVariants.calculate();
        long end = System.currentTimeMillis();

        System.out.printf("%nResults: %s%n", simpleRouteVariants);

        System.out.printf("Calculation time: %dms%n", end - start);

        StageRoute bestSimpleStageRoute = simpleRouteVariants.getBestRoute();
        System.out.printf("Best route %s%n", bestSimpleStageRoute);
    }
}