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

    private void readArgs(Options options) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            this.calculator = cmd.getOptionValue("c");
            this.minDistance = cmd.getParsedOptionValue("m");
            this.maxDistance = cmd.getParsedOptionValue("x");
            this.sampleName = cmd.getOptionValue("sn", DEFAULT_SAMPLE_NAME);

            if (cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("odp", options, true);
            }
        } catch (ParseException e) {
            throw new ParseException("Parsing failed. Reason: " + e.getMessage());
        }
    }
}
