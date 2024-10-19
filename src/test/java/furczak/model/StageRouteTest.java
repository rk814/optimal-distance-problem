package furczak.model;

import furczak.calculators.bruteforce.RecurrentRoutesCalculator;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.instancio.Select.field;


class StageRouteTest {

    private static Stream<Arguments> provideValues() {
        return Stream.of(
                Arguments.of(List.of(0, 2, 4, 6), 1, 2, 1.0, List.of(2, 2, 2)),
                Arguments.of(List.of(0, 2), 1, 3, 0.0, List.of(2)),
                Arguments.of(List.of(0, 20, 50, 60), 10, 30, 8.16496580927726, List.of(20, 30, 10)),
                Arguments.of(List.of(0, 100, 102, 104), 2, 100, 49.0, List.of(100, 2, 2)),
                Arguments.of(List.of(50, 100, 150), 50, 50, 0.0, List.of(50, 50))
        );
    }

    @ParameterizedTest
    @MethodSource("provideValues")
    void calculate_shouldCalculateRouteDistancesAndStandardDeviation_whenRequiredFieldsAreProperSet(
            List<Integer> testRoute, int min, int max, Double expectedStandardDeviation, List<Integer> expectedRouteDistances) {

        RecurrentRoutesCalculator calculator = new RecurrentRoutesCalculator();
        RouteVariants routeVariants = new RouteVariants(calculator, min, max);
        StageRoute testStageRoute = new StageRoute(testRoute, routeVariants);

        //when:
        testStageRoute.calculate();

        //then:
        Assertions.assertThat(testStageRoute).extracting(StageRoute::getStandardDeviation)
                .isEqualTo(expectedStandardDeviation);
        Assertions.assertThat(testStageRoute)
                .extracting(StageRoute::getRouteDistances, InstanceOfAssertFactories.list(Integer.class))
                .hasSize(expectedRouteDistances.size())
                .isEqualTo(expectedRouteDistances);
    }

    @Test
    void calculate_shouldThrowNoSuchElementException_whenRouteIsEmptyList() {
        //given:
        List<Integer> testRoute = List.of();
        RecurrentRoutesCalculator calculator = new RecurrentRoutesCalculator();
        int min = 1, max = 2;
        RouteVariants routeVariants = new RouteVariants(calculator, min, max);
        StageRoute testStageRoute = new StageRoute(testRoute, routeVariants);

        //when & then:
        Assertions.assertThatThrownBy(() -> testStageRoute.calculate())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void isRouteComplete_shouldReturnTrue_whenRouteLastPointMatchAvailablePointsLastPoint() throws NoSuchFieldException, IllegalAccessException {
        //given:
        List<Integer> testRoute = List.of(0, 1, 2, 3);
        RecurrentRoutesCalculator calculator = new RecurrentRoutesCalculator();
        RouteVariants routeVariants = new RouteVariants(calculator);
        StageRoute testStageRoute = new StageRoute(testRoute, routeVariants);

        Class<? extends RouteVariants> clazz = routeVariants.getClass();
        Field field = clazz.getDeclaredField("availablePoints");
        field.setAccessible(true);
        List<Integer> availablePoints = List.of(3);
        field.set(routeVariants, availablePoints);

        //when:
        boolean actual = testStageRoute.isRouteComplete();

        //then:
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void isRouteComplete_shouldReturnFalse_whenRouteLastPointNotMatchAvailablePointsLastPoint() throws NoSuchFieldException, IllegalAccessException {
        //given:
        List<Integer> testRoute = List.of(0, 1, 2, 3);
        RecurrentRoutesCalculator calculator = new RecurrentRoutesCalculator();
        RouteVariants routeVariants = new RouteVariants(calculator);
        StageRoute testStageRoute = new StageRoute(testRoute, routeVariants);

        Class<? extends RouteVariants> clazz = routeVariants.getClass();
        Field field = clazz.getDeclaredField("availablePoints");
        field.setAccessible(true);
        List<Integer> availablePoints = List.of(0);
        field.set(routeVariants, availablePoints);

        //when:
        boolean actual = testStageRoute.isRouteComplete();

        //then:
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void compareTo_shouldReturnOne_whenFirstStageRouteIsBiggerThenSecond() {
        //given:
        StageRoute stageRoute1 = Instancio.of(StageRoute.class)
                .set(field("standardDeviation"), 1.2)
                .create();
        StageRoute stageRoute2 = Instancio.of(StageRoute.class)
                .set(field("standardDeviation"), 0.8)
                .create();

        //when:
        int actual = stageRoute1.compareTo(stageRoute2);

        //then:
        Assertions.assertThat(actual).isEqualTo(1);
    }

    @Test
    void compareTo_shouldReturnZero_whenFirstStageRouteAndSecondAreEqual() {
        //given:
        StageRoute stageRoute1 = Instancio.of(StageRoute.class)
                .set(field("standardDeviation"), 1.2)
                .create();
        StageRoute stageRoute2 = Instancio.of(StageRoute.class)
                .set(field("standardDeviation"), 1.2)
                .create();

        //when:
        int actual = stageRoute1.compareTo(stageRoute2);

        //then:
        Assertions.assertThat(actual).isEqualTo(0);
    }

    @Test
    void compareTo_shouldReturnMinusOne_whenFirstStageRouteIsSmallerThenSecond() {
        //given:
        StageRoute stageRoute1 = Instancio.of(StageRoute.class)
                .set(field("standardDeviation"), 0.8)
                .create();
        StageRoute stageRoute2 = Instancio.of(StageRoute.class)
                .set(field("standardDeviation"), 1.2)
                .create();

        //when:
        int actual = stageRoute1.compareTo(stageRoute2);

        //then:
        Assertions.assertThat(actual).isEqualTo(-1);
    }

    @Test
    void getLastRoutePoint_shouldReturnLastPoint_whenRouteListIsNotEmpty() {
        //given:
        List<Integer> testRoute = List.of(0, 1, 2, 3);
        RecurrentRoutesCalculator calculator = new RecurrentRoutesCalculator();
        RouteVariants routeVariants = new RouteVariants(calculator);
        StageRoute testStageRoute = new StageRoute(testRoute, routeVariants);

        //when:
        int actual = testStageRoute.getLastRoutePoint();

        //then:
        Assertions.assertThat(actual).isEqualTo(3);
    }

    @Test
    void getLastRoutePoint_shouldReturnMinusOne_whenRouteListIsEmpty() {
        //given:
        List<Integer> testRoute = List.of();
        RecurrentRoutesCalculator calculator = new RecurrentRoutesCalculator();
        RouteVariants routeVariants = new RouteVariants(calculator);
        StageRoute testStageRoute = new StageRoute(testRoute, routeVariants);

        //when:
        int actual = testStageRoute.getLastRoutePoint();

        //then:
        Assertions.assertThat(actual).isEqualTo(-1);
    }

    @Test
    void toString_shouldReturnRouteIsEmptyMessage_whenRouteIsEmptyList() {
        //given:
        List<Integer> testRoute = List.of();
        RecurrentRoutesCalculator calculator = new RecurrentRoutesCalculator();
        RouteVariants routeVariants = new RouteVariants(calculator);
        StageRoute testStageRoute = new StageRoute(testRoute, routeVariants);

        //when:
        String actual = testStageRoute.toString();

        //then:
        Assertions.assertThat(actual).contains("Route", "empty");
    }

    @Test
    void toString_shouldReturnRouteMessageWithAdvice_whenRouteIsNotEmptyButCalculationsWereNotInvoked() {
        //given:
        List<Integer> testRoute = List.of(0, 1, 2, 3);
        RecurrentRoutesCalculator calculator = new RecurrentRoutesCalculator();
        RouteVariants routeVariants = new RouteVariants(calculator);
        StageRoute testStageRoute = new StageRoute(testRoute, routeVariants);

        //when:
        String actual = testStageRoute.toString();

        //then:
        Assertions.assertThat(actual).contains("Route", "calculate()");
    }

    @Test
    void toString_shouldReturnProperMessage_whenRouteIsNotEmptyAndCalculationsWereInvoked() {
        //given:
        StageRoute testStageRoute = Instancio.create(StageRoute.class);

        //when:
        String actual = testStageRoute.toString();

        //then:
        Assertions.assertThat(actual).contains("Route", "legs", "standard deviation");
    }
}
