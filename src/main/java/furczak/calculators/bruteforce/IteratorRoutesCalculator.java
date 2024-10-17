package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;
import furczak.model.StageRoute;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

@Setter
@Slf4j
public class IteratorRoutesCalculator extends RouteCalculator {

    @Override
    public List<StageRoute> calculateRoutes() {
        log.trace("Started calculateRoutes() method...");
        List<Integer> availablePoints = routeVariants.getAvailablePoints();

        List<StageRoute> routes = new ArrayList<>();
        routes.add(new StageRoute(List.of(routeVariants.getFirstAvailablePoint()), routeVariants));

        for (Integer nextPoint : availablePoints.subList(1,availablePoints.size())) {
            ListIterator<StageRoute> routesIterator = routes.listIterator();

            while (routesIterator.hasNext()) {
                StageRoute nextRoute = routesIterator.next();
                Integer lastRoutePoint = nextRoute.getRoute().get(nextRoute.getRoute().size() - 1);
                int maxValidPoint = lastRoutePoint + routeVariants.getDistanceBoundaries().getMax();
                int minValidPoint = lastRoutePoint + routeVariants.getDistanceBoundaries().getMin();

                if (maxValidPoint >= nextPoint && minValidPoint <= nextPoint) {
                    List<Integer> newList = Stream.concat(nextRoute.getRoute().stream(), Stream.of(nextPoint)).toList();
                    StageRoute newRoute = new StageRoute(newList, routeVariants);

                    if (isLastPoint(nextPoint)) routesIterator.set(newRoute);
                    else routesIterator.add(newRoute);
                }

                if (maxValidPoint < nextPoint || (minValidPoint > nextPoint && isLastPoint(nextPoint))) {
                    routesIterator.remove();
                }
            }
        }
        return routes;
    }

    private boolean isLastPoint(Integer nextPoint) {
        return nextPoint.equals(routeVariants.getLastAvailablePoint());
    }
}
