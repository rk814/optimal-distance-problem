package furczak.calculators;

import furczak.model.StageRoute;
import furczak.model.RouteVariants;

import java.util.List;

public interface RouteCalculator {
    List<StageRoute> calculateRoutes();

    void setRouteVariants(RouteVariants routeVariants);
}
