package furczak.options;

import lombok.Getter;
import org.apache.commons.cli.*;


@Getter
public class SolutionOptionsHandler {

    private final String DEFAULT_SAMPLE_NAME = "sample1.csv";
    private final boolean DEFAULT_IS_GENERATED = false;

    private final String[] args;

    private String calculator;
    private Integer minDistance;
    private Integer maxDistance;
    private String sampleName;


    public SolutionOptionsHandler(String[] args) throws ParseException {
        this.args = args;
        Options options = setupOptions();
        readArgs(options);
    }


    private Options setupOptions() {
        Options options = new Options();
        Option calc = new Option("c", "calc", true,
                "calculator type; currently 'iterator' or 'recurrent' (required)");
        Option min = Option.builder("m")
                .longOpt("min")
                .hasArg()
                .argName("int")
                .type(Integer.class)
                .desc("the minimum allowable distance (in days) for route calculations (required)").build();
        Option max = Option.builder("x")
                .longOpt("max")
                .hasArg()
                .argName("int")
                .type(Integer.class)
                .desc("the maximum allowable distance (in days) for route calculations (required)").build();
        Option sample = Option.builder("sn")
                .longOpt("sample")
                .hasArg()
                .argName("string")
                .desc("file containing sample points for route calculations")
                .build();
        Option help = Option.builder("h")
                .longOpt("help")
                .desc("this help")
                .build();

        options.addOption(calc);
        options.addOption(min);
        options.addOption(max);
        options.addOption(sample);
        options.addOption(help);

        return options;
    }

    public boolean areAllOptionsPresent() {
        return getMinDistance() != null && getMaxDistance() != null && getCalculator() != null && getSampleName() != null;
    }

    private void readArgs(Options options) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("optimal-distance-problem", options, true);
                return;
            }
            areRequiredArgsPresent(cmd);
            this.calculator = cmd.getOptionValue("c");
            this.minDistance = cmd.getParsedOptionValue("m");
            this.maxDistance = cmd.getParsedOptionValue("x");
            this.sampleName = cmd.getOptionValue("sn", DEFAULT_SAMPLE_NAME);

        } catch (ParseException e) {
            throw new ParseException("Parsing failed. Reason: " + e.getMessage());
        }
    }

    private void areRequiredArgsPresent(CommandLine cmd) throws ParseException {
        if (!cmd.hasOption("c")) {
            throw new ParseException("calc argument is required (-h for help)");
        }
        if (!cmd.hasOption("m")) {
            throw new ParseException("min argument is required (-h for help)");
        }
        if (!cmd.hasOption("x")) {
            throw new ParseException("max argument is required (-h for help)");
        }
    }
}
