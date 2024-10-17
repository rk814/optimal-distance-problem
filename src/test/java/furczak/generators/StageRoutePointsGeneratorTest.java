package furczak.generators;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

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
        Assertions.assertThatThrownBy(()-> generator.getSampleRoutePoints(key))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void addSampleRoutPoints_shouldAddNewList() {
        //given:
        int key = 111;
        List<Integer> testList = List.of(0, 23, 44, 57);

        //when:
        generator.addSampleRoutPoints(key,testList);
        Map<Integer, List<Integer>> actual = generator.getSampleLists();

        //then:
        Assertions.assertThat(actual)
                .containsKey(key)
                .extractingByKey(key, InstanceOfAssertFactories.list(Integer.class))
                .hasSize(testList.size())
                .containsExactlyElementsOf(testList);
    }

    @Test
    void generateRandomRoutePoints_shouldReturnRequestedList() {
        //given:
        int listLength = 10;
        int lastNumber= 111;

        //when:
        List<Integer> actual = generator.generateRandomRoutePoints(listLength, lastNumber);

        //then:
        Assertions.assertThat(actual)
                .isNotEmpty()
                .hasSize(listLength+2)
                .contains(0,lastNumber);
    }

    @Test
    void generateRandomRoutePoints_shouldReturnTwoElementsList() {
        //given:
        int listLength = 0;
        int lastNumber= 111;

        //when:
        List<Integer> actual = generator.generateRandomRoutePoints(listLength, lastNumber);

        //then:
        Assertions.assertThat(actual)
                .isNotEmpty()
                .hasSize(2)
                .containsExactly(0,lastNumber);
    }

    @Test
    void generateRandomRoutePoints_shouldThrowIllegalArgumentException() {
        //given:
        int listLength = 10;
        int lastNumber= 1;

        //when & then:
       Assertions.assertThatThrownBy(()->generator.generateRandomRoutePoints(listLength, lastNumber))
               .isInstanceOf(IllegalArgumentException.class);
    }
}