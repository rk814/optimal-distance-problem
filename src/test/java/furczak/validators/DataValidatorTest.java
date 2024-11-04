package furczak.validators;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;


class DataValidatorTest {

    private final DataValidator dataValidator = new DataValidator();

    @ParameterizedTest
    @ValueSource(strings = {"aaa.csv", "a.csv", "test.txt"})
    void isFileNameValid_shouldReturnTrue_whenGivenNamesIsValid(String name) {
        //when:
        boolean actual = dataValidator.isFileNameValid(name);

        //then:
        Assertions.assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "", "test"})
    void isFileNameValid_shouldReturnFalse_whenGivenNamesIsNotValid(String name) {
        //when:
        boolean actual = dataValidator.isFileNameValid(name);

        //then:
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void isFileNameValid_shouldReturnFalse_whenGivenNamesIsNull() {
        //given:
        String name=null;

        //when:
        boolean actual = dataValidator.isFileNameValid(name);

        //then:
        Assertions.assertThat(actual).isFalse();
    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
                Arguments.of("test"),
                Arguments.of(12),
                Arguments.of(List.of(0,1)),
                Arguments.of('c')
        );
    }

    @ParameterizedTest
    @MethodSource("provideData")
    <T> void isNull_shouldReturnFalse_whenDataIsNotNull(T data) {
        //when:
        boolean actual = dataValidator.isNull(data);

        //then:
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void isNull_shouldReturnTrue_whenDataIsNull() {
        //given:
        String data = null;

        //when:
        boolean actual = dataValidator.isNull(data);

        //then:
        Assertions.assertThat(actual).isTrue();
    }
}