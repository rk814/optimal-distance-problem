package furczak;

import furczak.calculators.bruteforce.IteratorRoutesCalculator;
import furczak.calculators.bruteforce.RecurrentRoutesCalculator;
import furczak.generators.RoutePointsGenerator;
import furczak.model.StageRoute;
import furczak.model.RouteVariants;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        RoutePointsGenerator generator = new RoutePointsGenerator();


        List<Integer> availablePoints = generator.getSampleRoutePoints(1);
//        List<Integer> availablePoints = generator.generateRandomRoutePoints(50, 400);
        System.out.printf("Points list: %s%n", availablePoints);

        RouteVariants simpleRouteVariants = new RouteVariants(new RecurrentRoutesCalculator(), 22, 44);
        simpleRouteVariants.setAvailablePoints(availablePoints);

        long start = System.currentTimeMillis();
        simpleRouteVariants.calculate();
        long end = System.currentTimeMillis();

        System.out.printf("%nResults: %s%n", simpleRouteVariants);

        System.out.printf("Calculation time: %dms%n", end-start);

        StageRoute bestSimpleStageRoute = simpleRouteVariants.getBestRoute();
        System.out.printf("Best route %s%n", bestSimpleStageRoute);
    }
}