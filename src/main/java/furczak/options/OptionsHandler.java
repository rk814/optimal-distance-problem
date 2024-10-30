package furczak.options;

import lombok.Getter;
import org.apache.commons.cli.*;

import java.util.Arrays;


@Getter
public class OptionsHandler {

    private final int DEFAULT_SAMPLE_NUMBER = 1;
    private final boolean DEFAULT_IS_GENERATED = false;
    private final int DEFAULT_NUMBER_OF_POINTS = 10;
    private final int DEFAULT_START_POINT = 0;
    private final int DEFAULT_END_POINT = 100;

    private final String[] args;

    private String calculator;
    private Integer minDistance;
    private Integer maxDistance;
    private Integer sampleNumber;
    private Boolean isGenerated;
    private Integer numberOfPoints;
    private Integer startPoint;
    private Integer endPoint;


    public OptionsHandler(String[] args) throws ParseException {
        this.args = args;
        Options options = setupOptions();
        readArgs(options);
    }


    private Options setupOptions() {
        Options options = new Options();
        Option calc = new Option("c", "calc", true,
                "calculator type; currently 'iterator' or 'recurrent'");
        calc.setRequired(true);
        Option min = Option.builder("m")
                .longOpt("min")
                .hasArg()
                .argName("int")
                .required()
                .type(Integer.class)
                .desc("the minimum allowable distance (in days) for route calculations").build();
        Option max = Option.builder("x")
                .longOpt("max")
                .hasArg()
                .argName("int")
                .required()
                .type(Integer.class)
                .desc("the maximum allowable distance (in days) for route calculations").build();
        Option sample = Option.builder("ns")
                .longOpt("sample")
                .hasArg()
                .argName("int")
                .type(Integer.class)
                .desc("number of predefined sample")
                .build();
        Option gen = Option.builder("g")
                .longOpt("gen")
                .desc("generate sample values").build();
        Option points = Option.builder("p")
                .longOpt("points")
                .hasArg()
                .argName("int")
                .type(Integer.class)
                .desc("the number of random intermediate points to generate between the start point and end point")
                .build();
        Option start = Option.builder("s")
                .longOpt("start")
                .hasArg()
                .argName("int")
                .type(Integer.class)
                .desc("the departure point, which must be equal or greater than 0")
                .build();
        Option end = Option.builder("e")
                .longOpt("end")
                .hasArg()
                .argName("int")
                .type(Integer.class)
                .desc("the destination point, which must be equal or greater than 2 and also greater than start point")
                .build();
        Option help = Option.builder("h")
                .longOpt("help")
                .desc("this help")
                .build();

        options.addOption(calc);
        options.addOption(min);
        options.addOption(max);
        options.addOption(sample);
        options.addOption(gen);
        options.addOption(points);
        options.addOption(start);
        options.addOption(end);
        options.addOption(help);

        return options;
    }

    private void readArgs(Options options) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            this.calculator = cmd.getOptionValue("c");
            this.minDistance = cmd.getParsedOptionValue("m");
            this.maxDistance = cmd.getParsedOptionValue("x");
            this.sampleNumber = cmd.getParsedOptionValue("ns", DEFAULT_SAMPLE_NUMBER);
            this.isGenerated = cmd.hasOption("g") ? true : DEFAULT_IS_GENERATED;
            this.numberOfPoints = cmd.getParsedOptionValue("p", DEFAULT_NUMBER_OF_POINTS);
            this.startPoint = cmd.getParsedOptionValue("s", DEFAULT_START_POINT);
            this.endPoint = cmd.getParsedOptionValue("e", DEFAULT_END_POINT);

            if (cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("odp", options, true);
            }
        } catch (ParseException e) {
            throw new ParseException("Parsing failed. Reason: " + e.getMessage());
        }
    }
}
