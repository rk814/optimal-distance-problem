package furczak.comparators;

import furczak.model.StageRoute;

import java.util.Comparator;

public class StandardDeviationComparator implements Comparator<StageRoute> {
    @Override
    public int compare(StageRoute route1, StageRoute route2) {
        return Double.compare(route1.getStandardDeviation(), route2.getStandardDeviation());
    }
}
