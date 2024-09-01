package furczak;

import furczak.calculators.bruteforce.RecurrentEtapCalculator;
import furczak.calculators.RouteCalculate;
import furczak.generators.RoutePointsGenerator;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        RoutePointsGenerator generator = new RoutePointsGenerator();

        List<Integer> simpleRoutePoints = generator.getSampleRoutePoints();
        System.out.printf("Points list: %s%n", simpleRoutePoints);
        RouteCalculate simpleCalculator = new RecurrentEtapCalculator(25, 45, simpleRoutePoints);
        List<List<Integer>> simpleResult = simpleCalculator.calculateRoutes();
        System.out.printf("Results: %s%n", simpleResult);
        List<Integer> bestSimpleSequence = simpleCalculator.getBestSequence(simpleResult);
        System.out.println(bestSimpleSequence);

//        List<Integer> complexRoutePoints = generator.generateRandomRoutePoints(100, 500);
//        System.out.printf("Points list: %s%n", complexRoutePoints);
//        RouteCalculate complexCalculator = new BruteForceRecurrentEtapSolution(complexRoutePoints);
//        List<List<Integer>> complexResult = complexCalculator.calculateRoutes();
//        System.out.printf("Results: %s%n", complexResult);
    }
}