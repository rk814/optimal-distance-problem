package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;
import furczak.model.DistanceBoundaries;
import furczak.model.RouteVariants;
import furczak.model.StageRoute;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class IteratorRoutesCalculatorTest {

    private RouteVariants routeVariants;
    private RouteCalculator calculator;

    @BeforeEach
    void setup() {
        this.routeVariants = Mockito.mock();
        this.calculator = new IteratorRoutesCalculator();
        calculator.setRouteVariants(routeVariants);
    }

    @Test
    void calculateRoutes_shouldReturnProperStageRouteListWithOneElement() {
        // given:
        List<Integer> availablePoints = List.of(15, 20, 30, 40);
        DistanceBoundaries distanceBoundaries = new DistanceBoundaries(15, 30);
        Mockito.when(routeVariants.getAvailablePoints()).thenReturn(availablePoints);
        Mockito.when(routeVariants.getLastAvailablePoint()).thenReturn(40);
        Mockito.when(routeVariants.getDistanceBoundaries()).thenReturn(distanceBoundaries);

        // when:
        List<StageRoute> actual = calculator.calculateRoutes();

        // then:
        Assertions.assertThat(actual).isNotEmpty()
                .hasSize(2)
                .extracting(StageRoute::getRoute)
                .contains(
                        List.of(0, 20, 40),
                        List.of(0, 15, 40)
                        );
    }

    @Test
    void calculateRoutes_shouldReturnProperStageRouteListWithTwoElements() {
        // given:
        List<Integer> availablePoints = List.of(20, 25, 30, 40);
        DistanceBoundaries distanceBoundaries = new DistanceBoundaries(15, 30);
        Mockito.when(routeVariants.getAvailablePoints()).thenReturn(availablePoints);
        Mockito.when(routeVariants.getLastAvailablePoint()).thenReturn(40);
        Mockito.when(routeVariants.getDistanceBoundaries()).thenReturn(distanceBoundaries);

        // when:
        List<StageRoute> actual = calculator.calculateRoutes();

        // then:
        Assertions.assertThat(actual).isNotEmpty()
                .hasSize(2)
                .extracting(StageRoute::getRoute)
                .contains(
                        List.of(0, 20, 40),
                        List.of(0, 25, 40));
    }

    @Test
    void calculateRoutes_shouldReturnProperStageRouteList_whenMinAndMaxDistancesHaveSameValue() {
        // given:
        List<Integer> availablePoints = List.of(15, 30, 45);
        DistanceBoundaries distanceBoundaries = new DistanceBoundaries(15, 15);
        Mockito.when(routeVariants.getAvailablePoints()).thenReturn(availablePoints);
        Mockito.when(routeVariants.getLastAvailablePoint()).thenReturn(45);
        Mockito.when(routeVariants.getDistanceBoundaries()).thenReturn(distanceBoundaries);

        // when:
        List<StageRoute> actual = calculator.calculateRoutes();

        // then:
        Assertions.assertThat(actual).isNotEmpty()
                .hasSize(1)
                .extracting(StageRoute::getRoute)
                .contains(List.of(0, 15, 30, 45));
    }

    @Test
    void calculateRoutes_shouldReturnEmptyList_whenThereIsNoAvailableRoutesForEntryData() {
        // given:
        List<Integer> availablePoints = List.of(15, 30, 45);
        DistanceBoundaries distanceBoundaries = new DistanceBoundaries(5, 10);
        Mockito.when(routeVariants.getAvailablePoints()).thenReturn(availablePoints);
        Mockito.when(routeVariants.getLastAvailablePoint()).thenReturn(40);
        Mockito.when(routeVariants.getDistanceBoundaries()).thenReturn(distanceBoundaries);

        // when:
        List<StageRoute> actual = calculator.calculateRoutes();

        // then:
        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    void calculateRoutes_shouldReturnProperStageRouteList_whenAvailablePointsAreOnlyValueOfOne() {
        // given:
        List<Integer> availablePoints = List.of(1);
        DistanceBoundaries distanceBoundaries = new DistanceBoundaries(1, 1);
        Mockito.when(routeVariants.getAvailablePoints()).thenReturn(availablePoints);
        Mockito.when(routeVariants.getLastAvailablePoint()).thenReturn(40);
        Mockito.when(routeVariants.getDistanceBoundaries()).thenReturn(distanceBoundaries);

        // when:
        List<StageRoute> actual = calculator.calculateRoutes();

        // then:
        Assertions.assertThat(actual).isNotEmpty();
    }

}