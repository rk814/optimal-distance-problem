package furczak.options;

import org.apache.commons.cli.ParseException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class GeneratorOptionsHandlerTest {

    @ParameterizedTest
    @CsvSource({
            "--points 100 --start 0 --end 150 --sample 'test.csv'",
            "-p 100 -s 0 -e 150 -sn 'test.csv'"
    })
    void OptionHandler_shouldBuildClassWithProperFieldsValues_whenAllOptionsPassed(String args) throws ParseException {
        //given:
        String[] testArgs = args.split(" ");

        //when:
        GeneratorOptionsHandler actual = new GeneratorOptionsHandler(testArgs);

        //then:
        Assertions.assertThat(actual).extracting(GeneratorOptionsHandler::getNumberOfPoints).asString().isEqualTo(testArgs[1]);
        Assertions.assertThat(actual).extracting(GeneratorOptionsHandler::getStartPoint).asString().isEqualTo(testArgs[3]);
        Assertions.assertThat(actual).extracting(GeneratorOptionsHandler::getEndPoint).asString().isEqualTo(testArgs[5]);
        Assertions.assertThat(actual).extracting(GeneratorOptionsHandler::getSampleName).asString().isEqualTo(testArgs[7]);
    }

    @ParameterizedTest
    @CsvSource({
            "--points 100 --start 0 --end 150",
            "-p 100 -s 0 -e 150"
    })
    void OptionHandler_shouldBuildClassWithProperFieldsValues_whenNecessaryOptionsArePassed(String args) throws ParseException {
        //given:
        String[] testArgs = args.split(" ");

        //when:
        GeneratorOptionsHandler actual = new GeneratorOptionsHandler(testArgs);

        //then:
        Assertions.assertThat(actual).extracting(GeneratorOptionsHandler::getNumberOfPoints).asString().isEqualTo(testArgs[1]);
        Assertions.assertThat(actual).extracting(GeneratorOptionsHandler::getStartPoint).asString().isEqualTo(testArgs[3]);
        Assertions.assertThat(actual).extracting(GeneratorOptionsHandler::getEndPoint).asString().isEqualTo(testArgs[5]);
        Assertions.assertThat(actual).extracting(GeneratorOptionsHandler::getSampleName).asString().isEqualTo(actual.getDEFAULT_SAMPLE_NAME());
    }

    @ParameterizedTest
    @CsvSource({
            "--",
            "--points 100",
            "--points 100 --start 0",
            "--points 100 --end 150",
            "--start 0 --end 150",
    })
    void OptionHandler_shouldThrowParseException_whenRequiredOptionsAreMissing(String args) {
        //given:
        String[] testArgs = args.split(" ");

        //when & then:
        Assertions.assertThatThrownBy(() -> new GeneratorOptionsHandler(testArgs)).isInstanceOf(ParseException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "--points 10.1 --start 0 --end 120",
            "--points 10 --start 0.5 --end 120",
            "--points 10 --start 0 --end 120.8",
    })
    void OptionHandler_shouldThrowParseException_whenArgsAreWrongType(String args) {
        //given:
        String[] testArgs = args.split(" ");

        //when & then:
        Assertions.assertThatThrownBy(() -> new GeneratorOptionsHandler(testArgs)).isInstanceOf(ParseException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "--points 10 --start 0 --end 120 --sample",
            "--points --start 0 --end 120 --sample 'test.csv'",
            "--points 10 --start --end 120 --sample 'test.csv'",
            "--points 10 --start 0 --end --sample 'test.csv'",
    })
    void OptionHandler_shouldThrowException_whenOptionsHaveMissingArgs(String args) {
        //given:
        String[] testArgs = args.split(" ");

        //when & then:
        Assertions.assertThatThrownBy(() -> new SolutionOptionsHandler(testArgs)).isInstanceOf(ParseException.class);
    }
}