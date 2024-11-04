package furczak;

import furczak.calculators.CalculatorFactory;
import furczak.model.RouteVariants;
import furczak.model.StageRoute;
import furczak.options.SolutionOptionsHandler;
import org.apache.commons.cli.ParseException;

import java.util.List;

public class Solution {

    private static final CalculatorFactory factory = new CalculatorFactory();

    public static void main(String[] args) throws ParseException {

        SolutionOptionsHandler options = new SolutionOptionsHandler(args);

        // setup
        int minDistance = options.getMinDistance();
        int maxDistance = options.getMaxDistance();
        String sampleName =  options.getSampleName();
        var calculator = factory.getCalculator(options.getCalculator()); // iterator or recurrent

        // todo load points
        List<Integer> availablePoints = null;

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