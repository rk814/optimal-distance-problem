package furczak;

import furczak.calculators.bruteforce.RecurrentRoutesCalculator;
import furczak.generators.RoutePointsGenerator;
import furczak.model.Route;
import furczak.model.RouteVariants;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        RoutePointsGenerator generator = new RoutePointsGenerator();


        List<Integer> simpleAvailablePoints = generator.getSampleRoutePoints();
        System.out.printf("Points list: %s%n", simpleAvailablePoints);

        RouteVariants simpleRouteVariants = new RouteVariants(new RecurrentRoutesCalculator(), 22, 48);
        simpleRouteVariants.setAvailablePoints(simpleAvailablePoints);
        simpleRouteVariants.calculate();

        System.out.printf("Results: %s%n", simpleRouteVariants);

        Route bestSimpleRoute = simpleRouteVariants.getBestRoute();
        System.out.printf("Best route %s%n", bestSimpleRoute);

//        List<Integer> complexRoutePoints = generator.generateRandomRoutePoints(100, 500);

    }
}