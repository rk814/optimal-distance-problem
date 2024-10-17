package furczak.model;

import furczak.calculators.bruteforce.RecurrentRoutesCalculator;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

import static org.instancio.Select.field;


class StageRouteTest {

    @Test
    void calculate_shouldCalculateRouteDistancesAndStandardDeviation() {
        //given:
        List<Integer> testRoute = List.of(0, 2, 4, 6);
        RecurrentRoutesCalculator calculator = new RecurrentRoutesCalculator();
        int min = 1, max = 2;
        RouteVariants routeVariants = new RouteVariants(calculator, min, max);
        StageRoute testStageRoute = new StageRoute(testRoute, routeVariants);

        //when:
        testStageRoute.calculate();

        //then:
        Assertions.assertThat(testStageRoute).extracting(StageRoute::getStandardDeviation)
                .isEqualTo(1.0);
        Assertions.assertThat(testStageRoute)
                .extracting(StageRoute::getRouteDistances, InstanceOfAssertFactories.list(Integer.class))
                .hasSize(3)
                .isEqualTo(List.of(2, 2, 2));
    }

    @Test
    void calculate_shouldThrowNoSuchElementException() {
        //given:
        List<Integer> testRoute = List.of();
        RecurrentRoutesCalculator calculator = new RecurrentRoutesCalculator();
        int min = 1, max = 2;
        RouteVariants routeVariants = new RouteVariants(calculator, min, max);
        StageRoute testStageRoute = new StageRoute(testRoute, routeVariants);

        //when & then:
        Assertions.assertThatThrownBy(()-> testStageRoute.calculate())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void isRouteComplete_shouldReturnTrue() throws NoSuchFieldException, IllegalAccessException {
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
    void isRouteComplete_shouldReturnFalse() throws NoSuchFieldException, IllegalAccessException {
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
    void compareTo_shouldReturn1() {
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
    void compareTo_shouldReturn0() {
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
    void compareTo_shouldReturnMinus1() {
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
    void getLastRoutePoint_shouldReturnLastPoint() {
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
    void getLastRoutePoint_shouldReturnMinus1() {
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
    void toString_shouldReturnRouteIsEmptyMessage() {
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
    void toString_shouldReturnRouteMessageWithAdvice() {
        //given:
        List<Integer> testRoute = List.of(0, 1,2,3);
        RecurrentRoutesCalculator calculator = new RecurrentRoutesCalculator();
        RouteVariants routeVariants = new RouteVariants(calculator);
        StageRoute testStageRoute = new StageRoute(testRoute, routeVariants);

        //when:
        String actual = testStageRoute.toString();

        //then:
        Assertions.assertThat(actual).contains("Route", "calculate()");
    }

    @Test
    void toString_shouldReturnProperMessage() {
        //given:
        StageRoute testStageRoute = Instancio.create(StageRoute.class);

        //when:
        String actual = testStageRoute.toString();

        //then:
        Assertions.assertThat(actual).contains("Route", "legs", "standard deviation");
    }
}
