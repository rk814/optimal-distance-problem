package furczak;

import furczak.calculators.CalculatorFactory;
import furczak.generators.RoutePointsGenerator;
import furczak.model.RouteVariants;
import furczak.model.StageRoute;
import furczak.options.OptionsHandler;
import org.apache.commons.cli.ParseException;

import java.util.List;

public class Odp {

    private static final CalculatorFactory factory = new CalculatorFactory();

    public static void main(String[] args) throws ParseException {

        OptionsHandler options = new OptionsHandler(args);

        // sample setup
        int sampleNumber = options.getSampleNumber();
        boolean generate = options.getIsGenerated();
        int numerOfPoints = options.getNumberOfPoints();
        int startPoint = options.getStartPoint();
        int endPoint = options.getEndPoint();

        // calculator setup
        int minDistance = options.getMinDistance();
        int maxDistance = options.getMaxDistance();
        var calculator = factory.getCalculator(options.getCalculator()); // iterator or recurrent


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