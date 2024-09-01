package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@Slf4j
public class RecurrentRoutesCalculator implements RouteCalculator {
//    private int minDistance = 30;
//    private int maxDistance = 50;
//
//    private final List<Integer> routePoints;


    public List<List<Integer>> calculateRoutes(List<Integer> availablePoints, int minDist, int maxDist) {
        List<Integer> innerList = List.of(0);
        return calculateRoutes(new ArrayList<>(List.of(innerList)), availablePoints, minDist, maxDist);
    }

    private List<List<Integer>> calculateRoutes(List<List<Integer>> routes, List<Integer> availablePoints, int minDist, int maxDist) {
        List<List<Integer>> result = new ArrayList<>();

        routes.forEach(r -> {
            int lastPoint = r.get(r.size() - 1);

            if (lastPoint == availablePoints.get(availablePoints.size() - 1)) {
                result.add(r);
            } else {
                List<List<Integer>> updatedList = availablePoints.stream()
                        .filter(p -> p >= lastPoint + minDist && p <= lastPoint + maxDist)
                        .map(p -> Stream.concat(r.stream(), Stream.of(p)).toList())
                        .toList();
                result.addAll(calculateRoutes(updatedList, availablePoints, minDist, maxDist));
            }
        });

        return result;
    }

//    public List<Integer> getBestSequence(List<List<Integer>> sequences) {
//        List<Double> sequencesDeviation = sequences.stream().map(this::getStandardDeviation).toList();
//        double bestSequenceDeviation = sequencesDeviation.stream().min(Double::compareTo)
//                .orElseThrow(() -> new NoSuchElementException("Empty sequences list"));
//        return sequences.get(sequencesDeviation.indexOf(bestSequenceDeviation));
//    }

//    private double getStandardDeviation(List<Integer> sequence) {
//        log.trace("Started getStandardDeviation method...");
//        List<Integer> etapDistances = IntStream.range(1, sequence.size())
//                .mapToObj(index -> sequence.get(index) - sequence.get(index - 1))
//                .toList();
//        log.debug("Etap distances: {}", etapDistances);
//        int etapCount = etapDistances.size();
//        log.debug("Etap count: {}", etapCount);
//        int perfectDistance = (minDistance + maxDistance) / 2;
//        log.debug("Perfect distance: {}", perfectDistance);
//
//        double sumOfSquaredDifferences = etapDistances.stream()
//                .mapToDouble(d -> d - perfectDistance)
//                .map(d -> d * d)
//                .reduce(Double::sum).orElseThrow(() ->
//                        new NoSuchElementException("Empty sequences list"));
//        log.debug("Sum of difference between distance and perfect distance squared: {}", sumOfSquaredDifferences);
//
//        double standardDeviation = Math.pow(sumOfSquaredDifferences / etapCount, 0.5);
//        log.info("Standard deviation of sequence: {} is {}", sequence, standardDeviation);
//        return standardDeviation;
//    }
}
