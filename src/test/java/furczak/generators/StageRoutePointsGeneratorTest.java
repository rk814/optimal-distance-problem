package furczak.generators;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


class StageRoutePointsGeneratorTest {

    private final RoutePointsGenerator generator = new RoutePointsGenerator();

    @Test
    void getSampleRoutePoints_shouldReturnRequestPointList() {
        //given:
        int key = 1;
        List<Integer> sampleList = generator.getSampleLists().get(key);
        int listSize = sampleList.size();

        //when:
        List<Integer> actual = generator.getSampleRoutePoints(key);

        //then:
        Assertions.assertThat(actual)
                .isNotEmpty()
                .hasSize(listSize)
                .isEqualTo(sampleList);
    }

    @Test
    void getSampleRoutePoints_shouldThrowNoSuchElementException() {
        //given:
        int key = 0;
        List<Integer> sampleList = generator.getSampleLists().get(key);

        //when&than:
        Assertions.assertThatThrownBy(() -> generator.getSampleRoutePoints(key))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void addSampleRoutPoints_shouldAddNewList() {
        //given:
        int key = 111;
        List<Integer> testList = List.of(0, 23, 44, 57);

        //when:
        generator.addSampleRoutPoints(key, testList);
        Map<Integer, List<Integer>> actual = generator.getSampleLists();

        //then:
        Assertions.assertThat(actual)
                .containsKey(key)
                .extractingByKey(key, InstanceOfAssertFactories.list(Integer.class))
                .hasSize(testList.size())
                .containsExactlyElementsOf(testList);
    }

    @ParameterizedTest
    @CsvSource({
            "10,0,111,12",
            "112,10,220,114",
            "2,0,89,4",
            "1000,0,2,1002",
            "200,0,2,202",
            "0,0,200,2"
    })
    void generateRandomRoutePoints_shouldReturnRequestedList(int listLength, int startNumber, int endNumber, int expectedListSize) {
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
    void generateRandomRoutePoints_shouldThrowIllegalArgumentException(int listLength, int startNumber, int endNumber) {
        //when & then:
        Assertions.assertThatThrownBy(() -> generator.generateRandomRoutePoints(listLength, startNumber, endNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }
}