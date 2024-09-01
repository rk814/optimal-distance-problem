package furczak.calculators;

import java.util.List;

public interface RouteCalculate {
    List<List<Integer>> calculateRoutes(List<List<Integer>> routes);

    List<List<Integer>> calculateRoutes();

    List<Integer> getBestSequence(List<List<Integer>> sequences);
}
