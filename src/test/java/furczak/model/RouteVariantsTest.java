package furczak.model;

import furczak.calculators.RouteCalculator;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.instancio.Select.field;


@ExtendWith(MockitoExtension.class)
class RouteVariantsTest {

    @Mock
    private RouteCalculator calculator;


    @Test
    void routeVariants_shouldCreateClassInstance_whenArgumentsAreValid() {
        //given
        int min = 1;
        int max = 5;

        //when:
        RouteVariants actual = new RouteVariants(calculator, min, max);

        //then:
        Assertions.assertThat(actual)
                .extracting(RouteVariants::getRouteCalculator).isEqualTo(calculator);
        Assertions.assertThat(actual)
                .extracting(RouteVariants::getDistanceBoundaries)
                .extracting(DistanceBoundaries::getMin).isEqualTo(min);
        Assertions.assertThat(actual)
                .extracting(RouteVariants::getDistanceBoundaries)
                .extracting(DistanceBoundaries::getMax).isEqualTo(max);
        Mockito.verify(calculator).setRouteVariants(actual);
    }

    @Test
    void calculate_shouldCalculateAndSortRoutes_whenRoutesMatchArguments() {
        //given:
        List<Integer> availablePoints = List.of(0, 15, 20, 25, 40);
        RouteVariants routeVariants = new RouteVariants(calculator, 15, 25);
        routeVariants.setAvailablePoints(availablePoints);

        List<StageRoute> resultOfCalculations = new ArrayList<>();
        StageRoute route1 = new StageRoute(List.of(0, 25, 40), routeVariants);
        StageRoute route2 = new StageRoute(List.of(0, 20, 40), routeVariants);
        resultOfCalculations.add(route1);
        resultOfCalculations.add(route2);
        Mockito.when(calculator.calculateRoutes()).thenReturn(resultOfCalculations);

        //when:
        routeVariants.calculate();

        //then:
        Mockito.verify(calculator, Mockito.times(1)).calculateRoutes();
        Assertions.assertThat(routeVariants.getCalculatedStageRoutes())
                .isNotEmpty()
                .contains(route1, route2)
                .allMatch(r -> r.getStandardDeviation() != null && r.getRouteDistances() != null);
        Assertions.assertThat(routeVariants.getCalculatedStageRoutes().get(0))
                .isLessThan(routeVariants.getCalculatedStageRoutes().get(1));
    }

    @Test
    void calculate_shouldSetEmptyList_whenNoRoutesMatchArguments() {
        //given:
        List<Integer> availablePoints = List.of(0, 15, 20, 25, 40);
        RouteVariants routeVariants = new RouteVariants(calculator, 1, 5);
        routeVariants.setAvailablePoints(availablePoints);

        List<StageRoute> resultOfCalculations = new ArrayList<>();
        Mockito.when(calculator.calculateRoutes()).thenReturn(resultOfCalculations);

        //when:
        routeVariants.calculate();

        //then:
        Mockito.verify(calculator, Mockito.times(1)).calculateRoutes();
        Assertions.assertThat(routeVariants.getCalculatedStageRoutes())
                .isEmpty();
    }

    @Test
    void calculate_shouldReturnNullPointerException_whenRequiredFieldsWereNotSetUpCorrectly() {
        //given:
        RouteVariants routeVariants = new RouteVariants(calculator);

        //when & then:
        Assertions.assertThatThrownBy(() -> routeVariants.calculate())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void getBestRoute_shouldReturnBestRoute_whenRoutesArePresentAndCalculated() throws NoSuchFieldException, IllegalAccessException {
        //given:
        RouteVariants routeVariants = new RouteVariants(calculator);
        Class<?> routeVariantsClass = routeVariants.getClass();
        Field field = routeVariantsClass.getDeclaredField("calculatedStageRoutes");
        field.setAccessible(true);
        List<StageRoute> testStageRoutes = List.of(
                Instancio.of(StageRoute.class).set(field("standardDeviation"), 5.5).create(),
                Instancio.of(StageRoute.class).set(field("standardDeviation"), 1.1).create(),
                Instancio.of(StageRoute.class).set(field("standardDeviation"), 2.8).create()
        );
        field.set(routeVariants, testStageRoutes);

        //when:
        StageRoute actual = routeVariants.getBestRoute();

        //then:
        Assertions.assertThat(actual)
                .isNotNull()
                .extracting(StageRoute::getStandardDeviation).isEqualTo(1.1);
    }

    @Test
    void getBestRoute_shouldThrowNoSuchElementException_whenRoutesAreNotPresent() throws NoSuchFieldException, IllegalAccessException {
        //given:
        RouteVariants routeVariants = new RouteVariants(calculator);
        Class<?> routeVariantsClass = routeVariants.getClass();
        Field field = routeVariantsClass.getDeclaredField("calculatedStageRoutes");
        field.setAccessible(true);
        List<StageRoute> testStageRoutes = List.of();
        field.set(routeVariants, testStageRoutes);

        //when & then:
        Assertions.assertThatThrownBy(() -> routeVariants.getBestRoute())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void getCalculatedStageRoutes_shouldReturnStageRoutesList_whenRoutesArePresent() throws NoSuchFieldException, IllegalAccessException {
        //given:
        RouteVariants routeVariants = new RouteVariants(calculator);
        Class<?> routeVariantsClass = routeVariants.getClass();
        Field field = routeVariantsClass.getDeclaredField("calculatedStageRoutes");
        field.setAccessible(true);
        List<StageRoute> testStageRoutes = List.of(
                Instancio.of(StageRoute.class).set(field("standardDeviation"), 1.1).create(),
                Instancio.of(StageRoute.class).set(field("standardDeviation"), 5.5).create(),
                Instancio.of(StageRoute.class).set(field("standardDeviation"), 2.8).create()
        );
        field.set(routeVariants, testStageRoutes);

        //when:
        List<StageRoute> actual = routeVariants.getCalculatedStageRoutes();

        //then:
        Assertions.assertThat(actual)
                .isNotNull()
                .hasSize(3);
    }

    @Test
    void getCalculatedStageRoutes_shouldThrowNullPointerException_whenRoutesAreNotCalculated() {
        //given:
        RouteVariants routeVariants = new RouteVariants(calculator);

        //when & then:
        Assertions.assertThatThrownBy(() -> routeVariants.getCalculatedStageRoutes())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void toString_shouldReturnProperInstanceMessage_whenCalculationsWerePerformed() throws NoSuchFieldException, IllegalAccessException {
        //given:
        RouteVariants routeVariants = new RouteVariants(calculator);
        Class<?> routeVariantsClass = routeVariants.getClass();

        Field stageRoutesField = routeVariantsClass.getDeclaredField("calculatedStageRoutes");
        stageRoutesField.setAccessible(true);
        List<StageRoute> testStageRoutes = Instancio.createList(StageRoute.class);
        stageRoutesField.set(routeVariants, testStageRoutes);

        Field divisionField = routeVariantsClass.getDeclaredField("distanceBoundaries");
        divisionField.setAccessible(true);
        DistanceBoundaries division = Instancio.create(DistanceBoundaries.class);
        divisionField.set(routeVariants, division);

        //when:
        String actual = routeVariants.toString();

        //then:
        Assertions.assertThat(actual).contains("Route", "perfect distance", "calculated with",
                "distance is set up between", "standard deviation");
    }

    @Test
    void toString_shouldReturnCalculationsNotStartedJetMessage_whenCalculationsWereNotPerformed() {
        //given:
        RouteVariants routeVariants = new RouteVariants(calculator);

        //when:
        String actual = routeVariants.toString();

        //then:
        Assertions.assertThat(actual).contains("not", "started", "jet");
    }
}