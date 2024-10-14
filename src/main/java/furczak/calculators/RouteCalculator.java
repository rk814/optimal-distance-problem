package furczak.calculators;

import furczak.model.RouteVariants;
import furczak.model.StageRoute;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@NoArgsConstructor
public abstract class RouteCalculator {

    protected RouteVariants routeVariants;


    public abstract List<StageRoute> calculateRoutes();

    public String getName() {
        String[] split = this.getClass().toString().split("\\.");
        return split[split.length - 1];
    }
}
