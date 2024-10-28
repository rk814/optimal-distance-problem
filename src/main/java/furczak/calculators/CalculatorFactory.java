package furczak.calculators;

import furczak.calculators.bruteforce.IteratorRoutesCalculator;
import furczak.calculators.bruteforce.RecurrentRoutesCalculator;

public class CalculatorFactory {
    public RouteCalculator getCalculator(String name) {
        return switch (name) {
            case "iterator" -> new IteratorRoutesCalculator();
            case "recurrent" -> new RecurrentRoutesCalculator();
            default -> throw new IllegalArgumentException("No such calculator");
        };
    }
}
