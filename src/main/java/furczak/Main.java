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


        List<Integer> simpleAvailablePoints = generator.getSampleRoutePoints(1);
        System.out.printf("Points list: %s%n", simpleAvailablePoints);

        RouteVariants simpleRouteVariants = new RouteVariants(new RecurrentRoutesCalculator(), 22, 44);
        simpleRouteVariants.setAvailablePoints(simpleAvailablePoints);
        simpleRouteVariants.calculate();

        System.out.printf("%nResults: %s%n", simpleRouteVariants);

        StageRoute bestSimpleStageRoute = simpleRouteVariants.getBestRoute();
        System.out.printf("Best route %s%n", bestSimpleStageRoute);

//        List<Integer> complexRoutePoints = generator.generateRandomRoutePoints(100, 500);

    }
}