package furczak;

import furczak.bruteforce.BruteForceRecurrencyEtapSolution;
import furczak.generators.RoutePointsGenerator;

public class Main {
    public static void main(String[] args) {

        RoutePointsGenerator generator = new RoutePointsGenerator();

        BruteForceRecurrencyEtapSolution bruteForce = new BruteForceRecurrencyEtapSolution(generator.getSampleRoutePoints());
        System.out.println(bruteForce.getRoutePoints());
        BruteForceRecurrencyEtapSolution bruteForce2 = new BruteForceRecurrencyEtapSolution(generator.generateRandomRoutePoints(1000, 10000));
        System.out.println(bruteForce2.getRoutePoints());

        System.out.println(bruteForce.calculateRoutes());
    }
}