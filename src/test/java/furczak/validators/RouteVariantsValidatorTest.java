package furczak.validators;

import furczak.validators.RouteVariantsValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class RouteVariantsValidatorTest {

    @Test
    void validateDistances_shouldNotThrowAnyException() {
        //given:
        int min = 5;
        int max = 10;
        RouteVariantsValidator validator = new RouteVariantsValidator();

        //when and then:
        Assertions.assertThatCode(() -> validator.validateDistances(min, max))
                .doesNotThrowAnyException();
    }

    @Test
    void validateDistances_shouldThrowIllegalArgumentException_whenMinOrMaxIsLowerThen1() {
        //given:
        int min = 0;
        int max = 10;
        RouteVariantsValidator validator = new RouteVariantsValidator();

        //when and then:
        Assertions.assertThatThrownBy(() -> validator.validateDistances(min, max))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Route distances must be greater then 0");
    }

    @Test
    void validateDistances_shouldThrowIllegalArgumentException_whenMinIsGreaterThanMax() {
        //given:
        int min = 20;
        int max = 10;
        RouteVariantsValidator validator = new RouteVariantsValidator();

        //when and then:
        Assertions.assertThatThrownBy(() -> validator.validateDistances(min, max))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Max value must be greater or equal to min value");
    }

    @Test
    void validateAvailablePoints_shouldNotThrowAnyException() {
        //given:
        List<Integer> points = List.of(23, 45, 67);
        RouteVariantsValidator validator = new RouteVariantsValidator();

        //when and then:
        Assertions.assertThatCode(() -> validator.validateAvailablePoints(points))
                .doesNotThrowAnyException();
    }

    @Test
    void validateAvailablePoints_shouldThrowIllegalArgumentException_whenPointListIsEmpty() {
        //given:
        List<Integer> points = new ArrayList<>();
        RouteVariantsValidator validator = new RouteVariantsValidator();

        //when and then:
        Assertions.assertThatThrownBy(() -> validator.validateAvailablePoints(points))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("List of available points must contain at least 2 points");
    }

    @Test
    void validateAvailablePoints_shouldThrowIllegalArgumentException_whenThereAreAnyPointsLowerThan0() {
        //given:
        List<Integer> points = List.of(-4, 0, 23, 45);
        RouteVariantsValidator validator = new RouteVariantsValidator();

        //when and then:
        Assertions.assertThatThrownBy(() -> validator.validateAvailablePoints(points))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("List of available points cannot contain values less than 0");
    }

    @Test
    void validateAvailablePoints_shouldThrowIllegalArgumentException_whenListOfPointIsNotSorted() {
        //given:
        List<Integer> points = List.of(23, 12, 45, 0);
        RouteVariantsValidator validator = new RouteVariantsValidator();

        //when and then:
        Assertions.assertThatThrownBy(() -> validator.validateAvailablePoints(points))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("List of available points must be in ascending order");
    }
}