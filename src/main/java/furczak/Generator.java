package furczak;

import furczak.generators.RoutePointsGenerator;
import furczak.options.GeneratorOptionsHandler;
import org.apache.commons.cli.ParseException;

import java.util.List;

public class Generator {

    public static void main(String[] args) throws ParseException {
        GeneratorOptionsHandler options = new GeneratorOptionsHandler(args);

        // setup
        String sampleName = options.getSampleName();
        int numerOfPoints = options.getNumberOfPoints();
        int startPoint = options.getStartPoint();
        int endPoint = options.getEndPoint();

        RoutePointsGenerator generator = new RoutePointsGenerator();
        List<Integer> availablePoints = generator.generateRandomRoutePoints(numerOfPoints, startPoint, endPoint);
        System.out.printf("Points list: %s%n", availablePoints);

        //todo save with sampleName
    }
}
