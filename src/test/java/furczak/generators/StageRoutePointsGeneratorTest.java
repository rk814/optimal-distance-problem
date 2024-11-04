package furczak.generators;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.List;


class StageRoutePointsGeneratorTest {

    private final RoutePointsGenerator generator = new RoutePointsGenerator();


    @ParameterizedTest
    @CsvSource({
            "10,0,111,12",
            "112,10,220,114",
            "2,0,89,4",
            "1000,0,2,1002",
            "200,0,2,202",
            "0,0,200,2"
    })
    void generateRandomRoutePoints_shouldReturnRequestList_whenArgumentsAreValid(
            int listLength, int startNumber, int endNumber, int expectedListSize) {
        //when:
        List<Integer> actual = generator.generateRandomRoutePoints(listLength, startNumber, endNumber);

        //then:
        Assertions.assertThat(actual)
                .isNotEmpty()
                .hasSize(expectedListSize)
                .contains(startNumber, endNumber);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "10,0,0",
            "10,5,2",
            "10,-2,5",
            "10,0,-3",
            "10,4,4"
    })
    void generateRandomRoutePoints_shouldThrowIllegalArgumentException_whenArgumentsAreNotValid(
            int listLength, int startNumber, int endNumber) {
        //when & then:
        Assertions.assertThatThrownBy(() -> generator.generateRandomRoutePoints(listLength, startNumber, endNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }
}