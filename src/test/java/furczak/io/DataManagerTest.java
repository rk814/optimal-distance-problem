package furczak.io;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

class DataManagerTest {

    private DataManager dataManager;

    @BeforeEach
    void setup() {
        this.dataManager = new DataManager();
    }

    private static Stream<Arguments> provideValidFileNamesWithExpectedResults() {
        return Stream.of(
                Arguments.of("test\\testSample1_valid.csv", List.of(0, 10, 20)),
                Arguments.of("test\\testSample2_valid.csv", List.of(0))
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidFileNamesWithExpectedResults")
    void loadData_shouldLoadData_whenFilePathAndDataAreValid(String fileName, List<Integer> result) {
        //when:
        List<Integer> actual = dataManager.loadData(fileName);

        //then:
        Assertions.assertThat(actual).containsExactlyElementsOf(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test\\testSample3.csv", "test\\testSample4.csv", "notExistingFile.csv"})
    void loadData_shouldThrowRuntimeException_whenFileDoseNotExistsOrContainInvalidData(String fileName) {
        //when & then:
        Assertions.assertThatThrownBy(() -> dataManager.loadData(fileName))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Loading failed");
    }

    @Test
    void loadData_shouldThrowRuntimeException_whenFileNameIsNull() {
        String fileName = null;
        //when & then:
        Assertions.assertThatThrownBy(() -> dataManager.loadData(fileName))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Loading failed");
    }

    private static Stream<Arguments> provideListWithIntegersValues() {
        return Stream.of(
                Arguments.of(List.of(0, 1, 2, 3)),
                Arguments.of(List.of(0))
        );
    }

    @ParameterizedTest
    @MethodSource("provideListWithIntegersValues")
    void saveData_shouldSaveFileWithProvidedData_whenArgsAreValid(List<Integer> testData) {
        //given:
        String fileName = "test\\save.csv";

        //when:
        deleteTestFileIfExist(fileName);
        dataManager.saveData(testData, fileName);
        String actual = loadTestFileContent(fileName);

        //then:
        Assertions.assertThat(actual).isEqualTo(String.join(",", testData.stream().map(String::valueOf).toArray(String[]::new)));
    }

    @Test
    void saveData_shouldThrowRuntimeException_whenDataIsNull() {
        //given:
        List<Integer> testData = null;
        String fileName = "test\\save.csv";

        //when & then:
        Assertions.assertThatThrownBy(() -> dataManager.saveData(testData, fileName))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Saving failed");
    }

    @Test
    void saveData_shouldThrowRuntimeException_whenFileNameIsNull() {
        //given:
        List<Integer> testData = List.of(1);
        String fileName = null;

        //when & then:
        Assertions.assertThatThrownBy(() -> dataManager.saveData(testData, fileName))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Saving failed");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "test\\?.csv",
            "test\\t/.csv",
            "test\\*777.csv",
            "test\\<>.csv",
            "",
            "test\\"
    })
    void saveData_shouldThrowRuntimeException_whenFileNameIsNotValid(String fileName) {
        //given:
        List<Integer> testData = List.of(1);

        //when & then:
        Assertions.assertThatThrownBy(() -> dataManager.saveData(testData, fileName))
                .isInstanceOf(RuntimeException.class);
    }


    private void deleteTestFileIfExist(String fileName) {
        File file = new File(dataManager.getSAMPLES_DIR(), fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private String loadTestFileContent(String fileName) {
        File file = new File(dataManager.getSAMPLES_DIR(), fileName);

        String result = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            result = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
