package furczak;

import furczak.bruteforce.BruteForceRecurrentEtapSolution;
import furczak.generators.RoutePointsGenerator;

public class Main {
    public static void main(String[] args) {

        RoutePointsGenerator generator = new RoutePointsGenerator();

        BruteForceRecurrentEtapSolution bruteForce = new BruteForceRecurrentEtapSolution(generator.getSampleRoutePoints());
        System.out.println(bruteForce.getRoutePoints());
        BruteForceRecurrentEtapSolution bruteForce2 = new BruteForceRecurrentEtapSolution(generator.generateRandomRoutePoints(1000, 10000));
        System.out.println(bruteForce2.getRoutePoints());

        System.out.println(bruteForce.calculateRoutes());
    }
}