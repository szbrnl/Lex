package agh.cs.lab;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextParser {

    private final TextPartType textPartType;

    private StringBuilder content = new StringBuilder();
    private StringBuilder name = new StringBuilder();
    private StringBuilder title = new StringBuilder();
    private LinkedHashMap<String, TextPart> children = new LinkedHashMap<>();

    public TextParser(TextPartType textPartType, StringBuilder text) {
        this.textPartType = textPartType;
        parseContent(text);
    }

    public String getName() {
        return name.toString();
    }

    public String getTitle() {
        return title.toString();
    }

    public String getContent() {
        return content.toString();
    }

    public LinkedHashMap<String, TextPart> getAllChildren() {
        return children;
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
            nextParts.stream().forEachOrdered(p-> children.put(p.getName(), p));

            //Content to pozostałości po wycinkach
            this.content = text;
        }
    }

    private void parseName(StringBuilder text) throws IllegalStateException{
        Matcher m;
        Pattern r = Pattern.compile(this.textPartType.getTextPartNameRegularExpression());
        m = r.matcher(text);

        m.find();

        //if(m.) throw new IllegalStateException("Could not find Name");

        this.name = new StringBuilder(m.group(0));

        text.replace(m.start(), m.end(), "");
    }

    private void parseTitle(StringBuilder text) throws IllegalStateException{

        String regexp = this.textPartType.getTextPartTitleRegularExpression();
        //if(regexp == "") throw new

        Matcher m;
        Pattern r = Pattern.compile(this.textPartType.getTextPartTitleRegularExpression());
        m = r.matcher(text);

        m.find();


        this.title = new StringBuilder(m.group(0));

        text.replace(m.start(), m.end(), "");
    }
}