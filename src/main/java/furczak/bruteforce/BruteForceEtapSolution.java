package furczak.bruteforce;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BruteForceEtapSolution {
    private int minDistance = 30;
    private int maxDistance = 50;

    private final List<Integer> routePoints;

    public BruteForceEtapSolution(List<Integer> routePoints) {
        this.routePoints = routePoints;
    }

    public BruteForceEtapSolution(int minDistance, int maxDistance, List<Integer> routePoints) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.routePoints = routePoints;
    }

    public List<List<Integer>> calculateRoutes() {
        List<List<Integer>> result = new ArrayList<>();
        result.add(List.of(0));
        boolean flag = true;

        while (flag) {
            int currentRoutesSize = result.size();

            for (int i = 0; i < currentRoutesSize; i++) {
                List<Integer> currentList = result.get(i);

                for (Integer point : routePoints) {
                    if (point > currentList.get(currentList.size() - 1) + minDistance &&
                            point < currentList.get(currentList.size() - 1) + maxDistance) {
                        List<Integer> newSublist = buildNewSubList(currentList, point);
                        result.add(newSublist);
                        flag = false;
                    } else {
//                        result.remove(currentList);
//                        result = result.stream().filter(l -> !l.equals(list)).toList();
                        flag = true;
                    }
                }
            }
        }
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
