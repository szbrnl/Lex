package agh.cs.lab;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextParser {

    //©Kancelaria Sejmu s. 50/73
    //2017-06-28

    private final TextPartType textPartType;

    private StringBuilder content = new StringBuilder();
    private ArrayList<String> contentList;
    private StringBuilder name = new StringBuilder();
    private StringBuilder title = new StringBuilder();
    private LinkedHashMap<String, TextPart> children = new LinkedHashMap<>();
    private int number;

    public TextParser(TextPartType textPartType, StringBuilder text) {
        this.textPartType = textPartType;
        parseContent(text);
        eraseUnnecessaryElements();
        normalizeWhitespaces();

        contentList = new ArrayList<>(
                Arrays.asList(
                        content.toString().split("\n"))
                        .stream().filter(s -> s.length() > 0)
                        .collect(Collectors.toList()
                        )
        );

        normalizeLines();
    }

    private void eraseUnnecessaryElements() {
        String[] unnecessaryElementsRegexps = new String[]{
                "©Kancelaria Sejmu\\n[\\S\\s]+\\n?",
                "O\ns\nr\n2\n3\np\nN",
                "©Kancelaria Sejmu s. \\d+/73\\n[\\S\\s]+\\n?"
        };

        for (String s : unnecessaryElementsRegexps) {
            Pattern r = Pattern.compile(s);
            Matcher m = r.matcher(content);
            content = new StringBuilder(m.replaceAll(""));
        }

    }

    public String getName() {
        return name.toString();
    }

    public String getTitle() {
        return title.toString();
    }

    public List<String> getContent() {
        return contentList;
    }

    public LinkedHashMap<String, TextPart> getAllChildren() {
        return children;
    }

    public int getNumber() {
        return number;
    }

    private void parseContent(StringBuilder text) {

        if (this.textPartType != TextPartType.Root) {


            try {
                parseName(text);
            } catch (IllegalStateException ex) {
                //Nie znaleziono wiec nie istnieje
                this.name = new StringBuilder("");
            }

            try {
                parseTitle(text);
            } catch (IllegalStateException ex) {
                this.title = new StringBuilder("");
            }

            try {
                parseNumber();
            } catch (IllegalStateException ex) {
                number = 0;
            }

        }


        //Parsuje po następnym typie
        TextPartType type = this.textPartType;
        Matcher m;
        Pattern r;

        //Sprawdz czy to już nie jest ostani rodzaj 'węzła'
        if (type.next() != TextPartType.END) {
            do {
                type = type.next();
                r = Pattern.compile(type.getTextPartRegularExpression());
                m = r.matcher(text);
            } while (!m.find() && type.next() != TextPartType.END);

            m.reset();


            List<TextPart> nextParts = new LinkedList<TextPart>();

            while (m.find()) {
                nextParts.add(new TextPart(type, new StringBuilder(m.group(0))));
            }

            //Wycinamy podelementy
            text = new StringBuilder(m.replaceAll(""));

            //Sprawdzamy, czy w tym co zostało nie ma czegoś do sparsowania
            if (type.next() != TextPartType.END && this.textPartType != TextPartType.Root) {
                TextPartType nextType = type.next();
                r = Pattern.compile(nextType.getTextPartRegularExpression());
                m = r.matcher(text);
                if (m.find()) {
                    m.reset();
                    children.putAll(new TextPart(type, text).children);
                }
                text = new StringBuilder(m.replaceAll(""));
            }

            //Wrzucanie listy do hashmapy -> bo skoro już sparsowane to mamy klucz
            nextParts.stream().forEachOrdered(p -> children.put(p.getName(), p));

            //Content to pozostałości po wycinkach
            this.content = text;
        }
    }

    private void parseNumber() {
        if (name.toString().matches("(.*)(\\d+)(.*)")) {
            Matcher matcher = Pattern.compile("\\d+").matcher(this.name.toString());
            matcher.find();
            this.number = Integer.parseInt(matcher.group(0));

        } else if (name.toString().matches(".* [XIV]+")) {
            Matcher matcher = Pattern.compile("[XIV]+").matcher(this.name.toString());
            matcher.find();
            this.number = fromRoman(new StringBuilder(matcher.group(0)));
        } else this.number = 0;
    }

    private void parseName(StringBuilder text) throws IllegalStateException {
        Matcher m;
        Pattern r = Pattern.compile(this.textPartType.getTextPartNameRegularExpression());
        m = r.matcher(text);

        m.find();

        this.name = new StringBuilder(m.group(0));

        text.replace(m.start(), m.end(), "");


    }

    private void parseTitle(StringBuilder text) throws IllegalStateException {
        Matcher m;
        Pattern r = Pattern.compile(this.textPartType.getTextPartTitleRegularExpression());
        m = r.matcher(text);

        m.find();

        this.title = new StringBuilder(m.group(0));

        text.replace(m.start(), m.end(), "");
    }

    private void normalizeWhitespaces() {

        while (title.length() > 0 && title.indexOf("\n") == 0)
            title.deleteCharAt(0);

        while (title.length() > 0 && title.indexOf("\n") == title.length() - 1)
            title.deleteCharAt(title.length() - 1);

        while (name.length() > 0 && name.indexOf("\n") == 0)
            name.deleteCharAt(0);

        while (name.length() > 0 && name.indexOf("\n") == name.length() - 1)
            name.deleteCharAt(name.length() - 1);

        while (content.indexOf("\n") == 0)
            content.deleteCharAt(0);

        while (content.length() > 0 && content.lastIndexOf("\n") == content.length() - 1)
            content.deleteCharAt(content.length() - 1);

        while (content.length() > 0 && content.indexOf(" ") == 0)
            content.deleteCharAt(0);

        while (content.length() > 0 && content.charAt(content.length() - 1) == ' ')
            content.deleteCharAt(content.length() - 1);

    }

    private void normalizeLines() {
        StringBuilder tmp = new StringBuilder();
        boolean somethingChanged = false;
        for (int i = 0; i < contentList.size(); i++) {
            StringBuilder contentListElement = new StringBuilder(contentList.get(i));
            somethingChanged = false;

            if (tmp.length() > 0) {
                tmp.append(contentListElement);
                contentListElement = tmp;
                tmp = new StringBuilder();
                somethingChanged = true;
            }

            //If the last character is a dash
            if (contentListElement.length() > 0 && contentListElement.charAt(contentListElement.length() - 1) == '-') {
                //Delete it
                contentListElement.deleteCharAt(contentListElement.length() - 1);
                //And find beginning of the last word
                int lastSpacePosition = contentListElement.lastIndexOf(" ");
                //Save last word to tmp
                tmp = new StringBuilder(contentListElement.substring(lastSpacePosition + 1));
                //Delete it from original line (with space)
                contentListElement.delete(lastSpacePosition, contentListElement.length());

                somethingChanged = true;
            }

            if (somethingChanged) {
                //Replace changed line with a new one
                contentList.set(i, contentListElement.toString());
            }
        }
    }

    public static int fromRoman(StringBuilder romanNumber) {
        romanNumber = new StringBuilder(romanNumber.toString().toUpperCase());

        if (romanNumber.length() == 0) return 0;
        if (romanNumber.substring(0, 1).equals("X")) return 10 + fromRoman(romanNumber.deleteCharAt(0));
        if (romanNumber.length() >1 && romanNumber.substring(0, 2).equals("IX")) return 9 + fromRoman(romanNumber.delete(0, 2));
        if (romanNumber.substring(0, 1).equals("V")) return 5 + fromRoman(romanNumber.delete(0, 1));
        if (romanNumber.length() >1 && romanNumber.substring(0, 2).equals("IV")) return 4 + fromRoman(romanNumber.delete(0, 2));
        if (romanNumber.substring(0, 1).equals("I")) return 1 + fromRoman(romanNumber.delete(0, 1));
        throw new IllegalArgumentException("Not valid roman number");
    }


}