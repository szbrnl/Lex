package agh.cs.lab;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {

    private final TextPartType textPartType;

    private StringBuilder content = new StringBuilder();
    private StringBuilder name = new StringBuilder();
    private StringBuilder title = new StringBuilder();
    private Set<AbstractTextPart> children = new LinkedHashSet<>();

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

    public Set<AbstractTextPart> getChildren() {
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
                    children.add(new TextPartContainer(type, text));
                }
                text = new StringBuilder(m.replaceAll(""));
            }

            //Wrzucanie listy do hashset -> bo skoro już sparsowane to mamy klucz
            children.addAll(nextParts);

            //Content to pozostałości po wycinkach
            this.content = text;
        }
    }

    private void parseName(StringBuilder text) throws IllegalStateException{
        Matcher m;
        Pattern r = Pattern.compile(this.textPartType.getTextPartNameRegularExpression());
        m = r.matcher(text);

        m.find();
        this.name = new StringBuilder(m.group(0));

        text.replace(m.start(), m.end(), "");
    }

    private void parseTitle(StringBuilder text) throws IllegalStateException{
        Matcher m;
        Pattern r = Pattern.compile(this.textPartType.getTextPartTitleRegularExpression());
        m = r.matcher(text);

        m.find();
        this.title = new StringBuilder(m.group(0));

        text.replace(m.start(), m.end(), "");
    }
}