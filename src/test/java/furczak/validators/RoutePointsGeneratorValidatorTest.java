package furczak.validators;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RoutePointsGeneratorValidatorTest {

    @Test
    void endPointValidator_shouldThrowIllegalArgumentException_whenStartPointIsLessThan0() {
        //given:
        RoutePointsGeneratorValidator generator = new RoutePointsGeneratorValidator();
        int start = -1;
        int end = 12;

        //when & then:
        Assertions.assertThatThrownBy(()-> generator.pointsValidator(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start point must be equal or greater than 0");
    }

    @Test
    void endPointValidator_shouldThrowIllegalArgumentException_whenEndPointIsLessThen2() {
        //given:
        RoutePointsGeneratorValidator generator = new RoutePointsGeneratorValidator();
        int start = 0;
        int end = 1;

        //when & then:
        Assertions.assertThatThrownBy(()-> generator.pointsValidator(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("End point must be greater than 1");
    }

    @ParameterizedTest
    @CsvSource({
            "5,4",
            "15,4",
            "4,4",
            "3,4",
    })
    void endPointValidator_shouldThrowIllegalArgumentException_whenEndPointIsNotGreaterThanStartPointBy2(int start, int end) {
        //given:
        RoutePointsGeneratorValidator generator = new RoutePointsGeneratorValidator();

        //when & then:
        Assertions.assertThatThrownBy(()-> generator.pointsValidator(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("End point must be greater start point at least by 2");
    }

    @ParameterizedTest
    @CsvSource({
            "0,2",
            "3,5",
            "10,104"
    })
    void endPointValidator_shouldNotThrowAnyException(int start, int end) {
        //given:
        RoutePointsGeneratorValidator generator = new RoutePointsGeneratorValidator();

        //when & then:
        Assertions.assertThatCode(()-> generator.pointsValidator(start, end))
                .doesNotThrowAnyException();

    }
}