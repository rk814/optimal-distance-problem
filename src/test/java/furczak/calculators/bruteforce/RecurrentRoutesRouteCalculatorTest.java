package furczak.calculators.bruteforce;

import furczak.calculators.RouteCalculator;
import furczak.model.DivisionDistance;
import furczak.model.RouteVariants;
import furczak.model.StageRoute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import org.assertj.core.api.Assertions;

class RecurrentRoutesRouteCalculatorTest {

    private RouteVariants routeVariants;
    private RouteCalculator calculator;

    @BeforeEach
    void setup() {
        this.routeVariants = Mockito.mock();
        this.calculator = new RecurrentRoutesRouteCalculator();
        calculator.setRouteVariants(routeVariants);
    }

    @Test
    void calculateRoutes_shouldReturnProperStageRouteListWithOneElement() {
        // given:
        List<Integer> availablePoints = List.of(20, 30, 40);
        DivisionDistance divisionDistance = new DivisionDistance(15, 30);
        Mockito.when(routeVariants.getAvailablePoints()).thenReturn(availablePoints);
        Mockito.when(routeVariants.getDivisionDistance()).thenReturn(divisionDistance);

        // when:
        List<StageRoute> actual = calculator.calculateRoutes();

        // then:
        Assertions.assertThat(actual).isNotEmpty()
                .hasSize(1)
                .containsExactly(new StageRoute(List.of(0, 20, 40), routeVariants));
    }

    @Test
    void calculateRoutes_shouldReturnProperStageRouteListWithTwoElements() {
        // given:
        List<Integer> availablePoints = List.of(20, 25, 30, 40);
        DivisionDistance divisionDistance = new DivisionDistance(15, 30);
        Mockito.when(routeVariants.getAvailablePoints()).thenReturn(availablePoints);
        Mockito.when(routeVariants.getDivisionDistance()).thenReturn(divisionDistance);

        // when:
        List<StageRoute> actual = calculator.calculateRoutes();

        // then:
        Assertions.assertThat(actual).isNotEmpty()
                .hasSize(2)
                .containsExactly(
                        new StageRoute(List.of(0, 20, 40), routeVariants),
                        new StageRoute(List.of(0, 25, 40), routeVariants));
    }

    @Test
    void calculateRoutes_shouldReturnProperStageRouteList_whenMinAndMaxDistancesHaveSameValue() {
        // given:
        List<Integer> availablePoints = List.of(15, 30, 45);
        DivisionDistance divisionDistance = new DivisionDistance(15, 15);
        Mockito.when(routeVariants.getAvailablePoints()).thenReturn(availablePoints);
        Mockito.when(routeVariants.getDivisionDistance()).thenReturn(divisionDistance);

        // when:
        List<StageRoute> actual = calculator.calculateRoutes();

        // then:
        Assertions.assertThat(actual).isNotEmpty()
                .hasSize(1)
                .containsExactly(new StageRoute(List.of(0, 15, 30, 45), routeVariants));
    }

    @Test
    void calculateRoutes_shouldReturnEmptyList_whenThereIsNoAvailableRoutesForEntryData() {
        // given:
        List<Integer> availablePoints = List.of(15, 30, 45);
        DivisionDistance divisionDistance = new DivisionDistance(5, 10);
        Mockito.when(routeVariants.getAvailablePoints()).thenReturn(availablePoints);
        Mockito.when(routeVariants.getDivisionDistance()).thenReturn(divisionDistance);

        // when:
        List<StageRoute> actual = calculator.calculateRoutes();

        // then:
        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    void calculateRoutes_shouldReturnProperStageRouteList_whenAvailablePointsAreOnlyValueOfOne() {
        // given:
        List<Integer> availablePoints = List.of(1);
        DivisionDistance divisionDistance = new DivisionDistance(1, 1);
        Mockito.when(routeVariants.getAvailablePoints()).thenReturn(availablePoints);
        Mockito.when(routeVariants.getDivisionDistance()).thenReturn(divisionDistance);

        // when:
        List<StageRoute> actual = calculator.calculateRoutes();

        // then:
        Assertions.assertThat(actual).isNotEmpty();
    }

}