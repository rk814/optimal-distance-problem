package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;
import furczak.model.StageRoute;
import furczak.model.RouteVariants;
import lombok.Setter;

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
@Setter
public class IteratorRoutesCalculator implements RouteCalculator {
    private RouteVariants routeVariants;

    @Override
    public List<StageRoute> calculateRoutes() {
        return null;
    }
}
