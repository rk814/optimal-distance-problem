package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;
import furczak.model.DistanceBoundaries;
import furczak.model.RouteVariants;
import furczak.model.StageRoute;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Stream;

abstract class CommonRoutesCalculatorTest {

    protected RouteVariants routeVariants;
    protected RouteCalculator calculator;

    protected abstract RouteCalculator createCalculator();

    @BeforeEach
    void setup() {
        this.routeVariants = Mockito.mock();
        this.calculator = createCalculator();
        calculator.setRouteVariants(routeVariants);
    }

    protected static Stream<Arguments> provideValues() {
        return Stream.of(
                Arguments.of(List.of(0, 15, 20, 30, 40), 15, 30, List.of(List.of(0, 20, 40), List.of(0, 15, 40))),
                Arguments.of(List.of(0, 15, 20, 30, 40), 10, 15, List.of(List.of(0, 15, 30, 40))),
                Arguments.of(List.of(0, 15, 20, 30, 40), 15, 20, List.of(List.of(0, 20, 40))),
                Arguments.of(List.of(0, 15, 20, 30, 40), 16, 30, List.of(List.of(0, 20, 40))),
                Arguments.of(List.of(0, 15, 20, 30, 40), 20, 30, List.of(List.of(0, 20, 40))),
                Arguments.of(List.of(0, 15, 20, 20, 40), 20, 30, List.of(List.of(0, 20, 40), List.of(0, 20, 40))),
                Arguments.of(List.of(0, 15, 20, 30, 40), 40, 60, List.of(List.of(0, 40))),
                Arguments.of(List.of(0, 20, 25, 30, 40), 15, 30, List.of(List.of(0, 20, 40), List.of(0, 25, 40))),
                Arguments.of(List.of(0, 15, 30, 45), 15, 15, List.of(List.of(0, 15, 30, 45))),
                Arguments.of(List.of(15, 30, 45), 15, 15, List.of(List.of(15, 30, 45))),
                Arguments.of(List.of(30, 45), 15, 15, List.of(List.of(30, 45))),
                Arguments.of(List.of(1), 1, 1, List.of(List.of(1)))
        );
    }


    @ParameterizedTest
    @MethodSource("provideValues")
    void calculateRoutes_shouldReturnStageRoutes_whenValidData(
            List<Integer> availablePoints, int min, int max, List<List<Integer>> result) {

        // given:
        DistanceBoundaries distanceBoundaries = new DistanceBoundaries(min, max);
        Mockito.when(routeVariants.getAvailablePoints()).thenReturn(availablePoints);
        Mockito.when(routeVariants.getLastAvailablePoint()).thenReturn(availablePoints.get(availablePoints.size() - 1));
        Mockito.when(routeVariants.getFirstAvailablePoint()).thenReturn(availablePoints.get(0));
        Mockito.when(routeVariants.getDistanceBoundaries()).thenReturn(distanceBoundaries);

        // when:
        List<StageRoute> actual = calculator.calculateRoutes();

        // then:
        Assertions.assertThat(actual).isNotEmpty()
                .hasSize(result.size())
                .extracting(StageRoute::getRoute)
                .containsExactlyInAnyOrderElementsOf(result);
    }

    protected static Stream<Arguments> provideValuesReturningEmptyList() {
        return Stream.of(
                Arguments.of(List.of(0, 15, 20, 30, 40), 15, 19),
                Arguments.of(List.of(0, 15, 20, 30, 40), 10, 14),
                Arguments.of(List.of(0, 15, 20, 30, 40), 21, 30),
                Arguments.of(List.of(0, 15, 30, 45), 5, 10),
                Arguments.of(List.of(0, 15, 30, 45), 46, 50)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValuesReturningEmptyList")
    void calculateRoutes_shouldReturnEmptyList_whenNoRoutesMatchArguments(
            List<Integer> availablePoints, int min, int max) {
        // given:
        DistanceBoundaries distanceBoundaries = new DistanceBoundaries(min, max);
        Mockito.when(routeVariants.getAvailablePoints()).thenReturn(availablePoints);
        Mockito.when(routeVariants.getLastAvailablePoint()).thenReturn(availablePoints.get(availablePoints.size() - 1));
        Mockito.when(routeVariants.getFirstAvailablePoint()).thenReturn(availablePoints.get(0));
        Mockito.when(routeVariants.getDistanceBoundaries()).thenReturn(distanceBoundaries);

        // when:
        List<StageRoute> actual = calculator.calculateRoutes();

        // then:
        Assertions.assertThat(actual).isEmpty();
    }
}
