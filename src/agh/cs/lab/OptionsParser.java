package agh.cs.lab;


import agh.cs.lab.cli.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OptionsParser {

    public final Options options = new Options();
    CommandLine cmd;

    public final Mode selectedMode;
    public final List<Mode> selectedModeArgs = new LinkedList<>();
    public final List<String> selectedModeValues = new LinkedList<>();
    public final String filePath;


    public OptionsParser(String[] args) throws ParseException, IllegalArgumentException {
        addOptions();

        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(options, args);

        checkOptions();

        if (cmd.hasOption("h")) {
            selectedMode = Mode.Help;
            filePath = "";
            return;
        }

        filePath = cmd.getOptionValue("f");

        //Content mode
        if (cmd.hasOption("c")) {
            selectedMode = Mode.Content;

            if (cmd.hasOption("S")) {
                selectedModeArgs.add(Mode.Section);
                selectedModeValues.add(cmd.getOptionValue("S"));
            }
            if (cmd.hasOption("C")) {
                selectedModeArgs.add(Mode.Chapter);
                selectedModeValues.add(cmd.getOptionValue("C"));
            }
            if (cmd.hasOption("A")) {
                selectedModeArgs.add(Mode.Article);
                selectedModeValues.add(cmd.getOptionValue("A"));
            }
            if (cmd.hasOption("P")) {
                selectedModeArgs.add(Mode.Paragraph);
                selectedModeValues.add(cmd.getOptionValue("P"));
            }
            if (cmd.hasOption("Po")) {
                selectedModeArgs.add(Mode.Point);
                selectedModeValues.add(cmd.getOptionValue("Po"));
            }
            if (cmd.hasOption("LPo")) {
                selectedModeArgs.add(Mode.LetterPoint);
                selectedModeValues.add(cmd.getOptionValue("LPo"));
            }
            if (cmd.hasOption("As")) {
                selectedModeArgs.add(Mode.Articles);
                selectedModeValues.addAll(Arrays.asList(cmd.getOptionValues("As")));
            }

            //Table of contents mode
        } else if (cmd.hasOption("tc")) {
            selectedMode = Mode.TableOfContents;

            if (cmd.hasOption("S")) {
                selectedModeArgs.add(Mode.Section);
                selectedModeValues.add(cmd.getOptionValue("S"));
            } else if (cmd.hasOption("C")) {
                selectedModeArgs.add(Mode.Chapter);
                selectedModeValues.add(cmd.getOptionValue("C"));
            } else {
                selectedModeArgs.add(Mode.Full);
                selectedModeValues.add("");
            }
        } else {
            throw new IllegalArgumentException("Wrong arguments");
        }

    }

    public void showHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("lex", options, true);
    }

    private void checkOptions() throws IllegalArgumentException {
        if (!cmd.hasOption("h") && !cmd.hasOption("f")) {
            throw new IllegalArgumentException("File not specified");
        }
        if(!cmd.hasOption("h")) {
            if (cmd.hasOption("c") && cmd.hasOption("tc")) {
                throw new IllegalArgumentException("Choose one from -c -tc");
            }

            if (cmd.hasOption("tc") && cmd.hasOption("Lpo")) {
                throw new IllegalArgumentException("Cannot combine -tc -LPo");
            }

            if (cmd.hasOption("tc") && cmd.hasOption("P")) {
                throw new IllegalArgumentException("Cannot combine -tc -P");
            }

            if (cmd.hasOption("tc") && cmd.hasOption("Po")) {
                throw new IllegalArgumentException("Cannot combine -tc -Po");
            }

            if (cmd.hasOption("tc") && cmd.hasOption("As")) {
                throw new IllegalArgumentException("Cannot combine -As -tc");
            }

            if (cmd.hasOption("As")) {
                String vals[] = cmd.getOptionValues("As");
                if (vals[0].equals(vals[1]))
                    throw new IllegalArgumentException("Wrong range argument");
            }

            if (!cmd.hasOption("c") && !cmd.hasOption("tc")) {
                throw new IllegalArgumentException("Mode must be specified");
            }
        }
    }

    private void addOptions() {
        options.addOption(Option.builder("h").desc("Show help").build());
        options.addOption(Option.builder("f").argName("Filepath").hasArg().desc("File to open").build());
        options.addOption(Option.builder("c").argName("Show content mode").desc("Show content of specified element(s)").build());
        options.addOption(Option.builder("tc").desc("Show table of contents of specified element\n" +
                "or use without additional parameters to show\n" +
                "table of contents for entire file").build());
        options.addOption(Option.builder("S").argName("Section").hasArg().desc("Select one section (\"dział\")").build());
        options.addOption(Option.builder("C").argName("Chapter").hasArg().desc("Select one chapter. Use number as in file").build());
        options.addOption(Option.builder("As").argName("Range of Articles").numberOfArgs(2).valueSeparator(' ').desc("Select Articles in given range separated by space, e.g. \"3 6\"").build());
        options.addOption(Option.builder("A").argName("Article").hasArg().desc("Select one Article").build());
        options.addOption(Option.builder("P").argName("Paragraph").hasArg().desc("Select one paragraph").build());
        options.addOption(Option.builder("Po").argName("Point").hasArg().desc("Select one point").build());
        options.addOption(Option.builder("LPo").argName("Letter Point").hasArg().desc("Select one letter point (e.g. \"a)\"").build());


    }

}
