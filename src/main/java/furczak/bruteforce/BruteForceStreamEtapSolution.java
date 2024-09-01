package furczak.bruteforce;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class BruteForceStreamEtapSolution {
    private int minDistance = 30;
    private int maxDistance = 50;

    private final List<Integer> routePoints;

    public BruteForceStreamEtapSolution(List<Integer> routePoints) {
        this.routePoints = routePoints;
    }

    public BruteForceStreamEtapSolution(int minDistance, int maxDistance, List<Integer> routePoints) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.routePoints = routePoints;
    }

    public List<List<Integer>> calculateRoutes() {
        List<List<Integer>> result = new ArrayList<>();
        result.add(List.of(0));
        boolean flag = true;

//        while (flag) {
//
//            result = result.stream().map(l -> {
//                // TODO
//                List<List<Integer>> xxx = routePoints.stream().filter().map(i -> Arrays.asList(i, i * 2, i * 3)).toList();
////                flag = true;
//
//                return null;
//            });
//
//        }
        return result;
    }

    private List<Integer> buildNewSubList(List<Integer> currentList, Integer point) {
        currentList.add(point);
        return currentList;
    }

    public List<Integer> getBestSequence(List<List<Integer>> sequences) {
        //TODO
        return null;
    }
}
