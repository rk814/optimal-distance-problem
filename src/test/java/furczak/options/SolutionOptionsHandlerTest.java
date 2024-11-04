package furczak.options;

import org.apache.commons.cli.ParseException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class SolutionOptionsHandlerTest {

    @ParameterizedTest
    @CsvSource({
            "--calc recurrent --min 20 --max 44 --sample 'test.csv'",
            "-c recurrent -m 20 -x 44 -sn 'test.csv'",
    })
    void OptionHandler_shouldBuildClassWithProperFieldsValues_whenAllOptionsPassed(String args) throws ParseException {
        //given:
        String[] testArgs = args.split(" ");

        //when:
        SolutionOptionsHandler actual = new SolutionOptionsHandler(testArgs);

        //then:
        Assertions.assertThat(actual).extracting(SolutionOptionsHandler::getCalculator).isEqualTo(testArgs[1]);
        Assertions.assertThat(actual).extracting(SolutionOptionsHandler::getMinDistance).asString().isEqualTo(testArgs[3]);
        Assertions.assertThat(actual).extracting(SolutionOptionsHandler::getMaxDistance).asString().isEqualTo(testArgs[5]);
        Assertions.assertThat(actual).extracting(SolutionOptionsHandler::getSampleName).asString().isEqualTo(testArgs[7]);
    }

    @ParameterizedTest
    @CsvSource({
            "--calc recurrent --min 20 --max 44",
            "-c recurrent -m 20 -x 44",
    })
    void OptionHandler_shouldBuildClassWithProperFieldsValues_whenNecessaryOptionsArePassed(String args) throws ParseException {
        //given:
        String[] testArgs = args.split(" ");

        //when:
        SolutionOptionsHandler actual = new SolutionOptionsHandler(testArgs);

        //then:
        Assertions.assertThat(actual).extracting(SolutionOptionsHandler::getCalculator).isEqualTo(testArgs[1]);
        Assertions.assertThat(actual).extracting(SolutionOptionsHandler::getMinDistance).asString().isEqualTo(testArgs[3]);
        Assertions.assertThat(actual).extracting(SolutionOptionsHandler::getMaxDistance).asString().isEqualTo(testArgs[5]);
        Assertions.assertThat(actual).extracting(SolutionOptionsHandler::getSampleName).isEqualTo(actual.getDEFAULT_SAMPLE_NAME());
    }

    @ParameterizedTest
    @CsvSource({
            "--",
            "--calc recurrent",
            "--calc recurrent --min 20",
            "--calc recurrent --max 44",
            "--min 20 --max 44",
    })
    void OptionHandler_shouldThrowParseException_whenRequiredOptionsAreMissing(String args) {
        //given:
        String[] testArgs = args.split(" ");

        //when & then:
        Assertions.assertThatThrownBy(() -> new SolutionOptionsHandler(testArgs)).isInstanceOf(ParseException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "--calc recurrent --min 20 --max '44' --sample 'test.csv'",
            "--calc recurrent --min 20.5 --max 44 --sample 'test.csv'",
    })
    void OptionHandler_shouldThrowParseException_whenArgsAreWrongType(String args) {
        //given:
        String[] testArgs = args.split(" ");

        //when & then:
        Assertions.assertThatThrownBy(() -> new SolutionOptionsHandler(testArgs)).isInstanceOf(ParseException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "--calc --min 20 --max 44 --sample 'test.csv'",
            "--calc recurrent --min 20 --max 44 --sample",
            "--calc recurrent --min --max 44 --sample 'test.csv'",
            "--calc recurrent --min 20 --max --sample 'test.csv'",
    })
    void OptionHandler_shouldThrowException_whenOptionsHaveMissingArgs(String args) {
        //given:
        String[] testArgs = args.split(" ");

        //when & then:
        Assertions.assertThatThrownBy(() -> new SolutionOptionsHandler(testArgs)).isInstanceOf(ParseException.class);
    }
}