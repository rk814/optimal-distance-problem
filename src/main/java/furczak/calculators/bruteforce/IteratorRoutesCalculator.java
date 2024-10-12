package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;

import java.util.List;

// TODO

/**
 * Plan:
 * Start nowa lista z listą 0
 * Iterator na liście przez kolejne int (hotele)
 * While
 * przez wszystkie listy dla danego hotelu
 * - każda iteracja to nowa lista
 * - jesli ogległość jest za duża to remove
 */
public class IteratorRoutesCalculator implements RouteCalculator {
    @Override
    public List<List<Integer>> calculateRoutes(List<Integer> availablePoints, int minDist, int maxDist) {
        return null;
    }
}
