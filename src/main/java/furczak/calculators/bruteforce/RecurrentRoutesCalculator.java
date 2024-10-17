package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;
import furczak.model.StageRoute;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Setter
@Slf4j
@NoArgsConstructor
public class RecurrentRoutesCalculator extends RouteCalculator {

    @Override
    public List<StageRoute> calculateRoutes() {
        log.trace("Started calculateRoutes() method...");
        List<StageRoute> startStageRouteList = new ArrayList<>();
        startStageRouteList.add(new StageRoute(List.of(routeVariants.getFirstAvailablePoint()), routeVariants));
        return calculateRoutesWithRecurrence(startStageRouteList);
    }

    private List<StageRoute> calculateRoutesWithRecurrence(List<StageRoute> stageRoutes) {
        List<StageRoute> result = new ArrayList<>();

        stageRoutes.forEach(route -> {
            int lastRoutePoint = route.getLastRoutePoint();

            if (route.isRouteComplete()) {
                result.add(route);
            } else {
                List<StageRoute> updatedList = routeVariants.getAvailablePoints().stream()
                        .filter(point -> point >= lastRoutePoint + routeVariants.getDistanceBoundaries().getMin()
                                && point <= lastRoutePoint + routeVariants.getDistanceBoundaries().getMax())
                        .map(point -> Stream.concat(route.getRoute().stream(), Stream.of(point)).toList())
                        .map(list -> new StageRoute(list, routeVariants))
                        .toList();

                if (updatedList.isEmpty()) {
                    log.info("Route: {} could not be completed", route.getRoute());
                }
                result.addAll(calculateRoutesWithRecurrence(updatedList));
            }
        });

        return result;
    }
}
