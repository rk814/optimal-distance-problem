package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;
import furczak.model.StageRoute;
import furczak.model.RouteVariants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Setter
@NoArgsConstructor
public class RecurrentRoutesCalculator implements RouteCalculator {

    private RouteVariants routeVariants;


    /**
     * Calculates routes with recurrent method.
     * <p>
     * Calculations are initialised with list of stage routes with one start stage route containing one point of 0 integer value.
     * This list is updated during each pass of the method and returned as the final result.
     * The initialization and recursive logic are handled by the private method `calculateRoutesWithRecurrence()`.
     * </p>
     * @return list with stage routes
     */
    public List<StageRoute> calculateRoutes() {
        List<StageRoute> startStageRouteList = new ArrayList<>();
        startStageRouteList.add(new StageRoute(List.of(0), routeVariants));
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
                        .filter(point -> point >= lastRoutePoint + routeVariants.getDivisionSetup().getMinDistance()
                                && point <= lastRoutePoint + routeVariants.getDivisionSetup().getMaxDistance())
                        .map(point -> Stream.concat(route.getRoute().stream(), Stream.of(point)).toList())
                        .map(list -> new StageRoute(list, routeVariants))
                        .toList();
                result.addAll(calculateRoutesWithRecurrence(updatedList));
            }
        });

        return result;
    }
}
