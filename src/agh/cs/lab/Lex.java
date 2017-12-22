package agh.cs.lab;

import agh.cs.lab.cli.ParseException;

import java.io.*;
import java.util.stream.Collectors;

public class Lex {
    public static void main(String args[]) {

        OptionsParser optionsParser = null;

        try {
            optionsParser = new OptionsParser(args);

            if (optionsParser.selectedMode == Mode.Help) {
                optionsParser.showHelp();
                return;
            }

            StringBuilder text = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(optionsParser.filePath));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }

            DocumentRoot root = new DocumentRoot(text);

            if (optionsParser.selectedMode == Mode.TableOfContents) {
                if (optionsParser.selectedModeArgs.get(0) == Mode.Full) {
                    System.out.println(root.getTableOfContents());
                } else {
                    System.out.println(root.getTableOfContents(optionsParser.selectedModeArgs.get(0).toTextPartType(),
                            optionsParser.selectedModeValues.get(0)));
                }
            } else if (optionsParser.selectedMode == Mode.Content) {
                if (optionsParser.selectedModeArgs.get(0) == Mode.Articles) {
                    System.out.println(
                            root.getArticlesInRange(optionsParser.selectedModeValues.get(0), optionsParser.selectedModeValues.get(1))
                    );
                } else {
                    System.out.println(
                            root.getElementAsString(
                                    optionsParser.selectedModeArgs.stream().map(x -> x.toTextPartType()).collect(Collectors.toList()).toArray(new TextPartType[0]),
                                    optionsParser.selectedModeValues.toArray(new String[0])
                            )
                    );
                }
            }

        } catch (ParseException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println("There was problem with file " + optionsParser.filePath + "\n" + ex.getMessage());
        }
    }

}
