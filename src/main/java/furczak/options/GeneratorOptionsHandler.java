package furczak.options;

import lombok.Getter;
import org.apache.commons.cli.*;


@Getter
public class GeneratorOptionsHandler {

    private final int DEFAULT_NUMBER_OF_POINTS = 10;
    private final int DEFAULT_START_POINT = 0;
    private final int DEFAULT_END_POINT = 100;
    private final String DEFAULT_SAMPLE_NAME = "sample.csv";

    private final String[] args;

    private Integer numberOfPoints;
    private Integer startPoint;
    private Integer endPoint;
    private String sampleName;


    public GeneratorOptionsHandler(String[] args) throws ParseException {
        this.args = args;
        Options options = setupOptions();
        readArgs(options);
    }


    private Options setupOptions() {
        Options options = new Options();
        Option points = Option.builder("p")
                .longOpt("points")
                .hasArg()
                .argName("int")
                .type(Integer.class)
                .desc("Specifies the number of random intermediate points to generate between the start and end points.")
                .build();
        Option start = Option.builder("s")
                .longOpt("start")
                .hasArg()
                .argName("int")
                .type(Integer.class)
                .desc("Sets the departure point, which must be at least 0.")
                .build();
        Option end = Option.builder("e")
                .longOpt("end")
                .hasArg()
                .argName("int")
                .type(Integer.class)
                .desc("Sets the destination point, which must be at least 2 and greater than the start point.")
                .build();
        Option sample = Option.builder("sn")
                .longOpt("sample")
                .hasArg()
                .argName("file")
                .desc("Specifies the file containing sample points for route calculations.")
                .build();
        Option help = Option.builder("h")
                .longOpt("help")
                .desc("Display this help message.")
                .build();

        options.addOption(points);
        options.addOption(start);
        options.addOption(end);
        options.addOption(sample);
        options.addOption(help);

        return options;
    }

    public boolean areAllOptionsPresent() {
        return getNumberOfPoints() != null && getStartPoint() != null && getEndPoint() != null;
    }

    private void readArgs(Options options) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("generator", options, true);
                return;
            }
            areRequiredArgsPresent(cmd);
            this.numberOfPoints = cmd.getParsedOptionValue("p", DEFAULT_NUMBER_OF_POINTS);
            this.startPoint = cmd.getParsedOptionValue("s", DEFAULT_START_POINT);
            this.endPoint = cmd.getParsedOptionValue("e", DEFAULT_END_POINT);
            this.sampleName = cmd.getOptionValue("sn", DEFAULT_SAMPLE_NAME);
        } catch (ParseException e) {
            throw new ParseException("Parsing failed. Reason: " + e.getMessage());
        }
    }

    private void areRequiredArgsPresent(CommandLine cmd) throws ParseException {
        if (!cmd.hasOption("p")) {
            throw new ParseException("points argument is required (-h for help)");
        }
        if (!cmd.hasOption("s")) {
            throw new ParseException("start argument is required (-h for help)");
        }
        if (!cmd.hasOption("e")) {
            throw new ParseException("end argument is required (-h for help)");
        }
    }
}
