package furczak.options;

import org.apache.commons.cli.ParseException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class OptionsHandlerTest {

    @ParameterizedTest
    @CsvSource({
            "--calc recurrent --min 20 --max 44 --sample 2 --gen --points 10 --start 0 --end 120",
            "-c recurrent -m 20 -x 44 -ns 2 -g -p 10 -s 0 -e 120",
    })
    void OptionHandler_shouldBuildClassWithProperFieldsValues_whenAllOptionsPassed(String args) throws ParseException {
        //given:
        String[] testArgs = args.split(" ");

        //when:
        OptionsHandler actual = new OptionsHandler(testArgs);

        //then:
        Assertions.assertThat(actual).extracting(OptionsHandler::getCalculator).isEqualTo(testArgs[1]);
        Assertions.assertThat(actual).extracting(OptionsHandler::getMinDistance).asString().isEqualTo(testArgs[3]);
        Assertions.assertThat(actual).extracting(OptionsHandler::getMaxDistance).asString().isEqualTo(testArgs[5]);
        Assertions.assertThat(actual).extracting(OptionsHandler::getSampleNumber).asString().isEqualTo(testArgs[7]);
        Assertions.assertThat(actual).extracting(OptionsHandler::getIsGenerated, InstanceOfAssertFactories.BOOLEAN).isTrue();
        Assertions.assertThat(actual).extracting(OptionsHandler::getNumberOfPoints).asString().isEqualTo(testArgs[10]);
        Assertions.assertThat(actual).extracting(OptionsHandler::getStartPoint).asString().isEqualTo(testArgs[12]);
        Assertions.assertThat(actual).extracting(OptionsHandler::getEndPoint).asString().isEqualTo(testArgs[14]);
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
        OptionsHandler actual = new OptionsHandler(testArgs);

        //then:
        Assertions.assertThat(actual).extracting(OptionsHandler::getCalculator).isEqualTo(testArgs[1]);
        Assertions.assertThat(actual).extracting(OptionsHandler::getMinDistance).asString().isEqualTo(testArgs[3]);
        Assertions.assertThat(actual).extracting(OptionsHandler::getMaxDistance).asString().isEqualTo(testArgs[5]);
        Assertions.assertThat(actual).extracting(OptionsHandler::getSampleNumber).isEqualTo(actual.getDEFAULT_SAMPLE_NUMBER());
        Assertions.assertThat(actual).extracting(OptionsHandler::getIsGenerated, InstanceOfAssertFactories.BOOLEAN).isFalse();
        Assertions.assertThat(actual).extracting(OptionsHandler::getNumberOfPoints).isEqualTo(actual.getDEFAULT_NUMBER_OF_POINTS());
        Assertions.assertThat(actual).extracting(OptionsHandler::getStartPoint).isEqualTo(actual.getDEFAULT_START_POINT());
        Assertions.assertThat(actual).extracting(OptionsHandler::getEndPoint).isEqualTo(actual.getDEFAULT_END_POINT());
    }

    // TODO
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
        Assertions.assertThatThrownBy(() -> new OptionsHandler(testArgs)).isInstanceOf(ParseException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "--calc --min 20 --max 44 --sample 2 --gen --points 10 --start 0 --end 120",
            "--calc recurrent --min 20.5 --max 44 --sample 2 --gen --points 10 --start 0 --end 120",
            "--calc recurrent --min 20 --max 44 --sample two --gen --points 10 --start 0 --end 120",
            "--calc recurrent --min 20 --max 44 --sample 2 --gen --points ten --start 0 --end 120",
            "--calc recurrent --min 20 --max 44 --sample 2 --gen --points 10 --start 0.5 --end 120",
    })
    void OptionHandler_shouldThrowParseException_whenArgsAreWrongType(String args) {
        //given:
        String[] testArgs = args.split(" ");

        //when & then:
        Assertions.assertThatThrownBy(() -> new OptionsHandler(testArgs)).isInstanceOf(ParseException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "--calc --min 20 --max 44 --sample 2 --gen --points 10 --start 0 --end 120",
            "--calc recurrent --min 20 --max 44 --sample 2 --gen --points 10 --start --end",
    })
    void OptionHandler_shouldThrowException_whenOptionsHaveMissingArgs(String args) {
        //given:
        String[] testArgs = args.split(" ");

        //when & then:
        Assertions.assertThatThrownBy(() -> new OptionsHandler(testArgs)).isInstanceOf(ParseException.class);
    }
}