package agh.cs.lab;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextPart {
    public final TextPartType textPartType;

    private String name;    //np ROZDZIAL III
    private String title;   //np. ŹRÓDŁA PRAWA
    private String content; //to co zostało - np preambuła

    private Map<String, TextPart> children = new LinkedHashMap<>();


    public TextPart(TextPartType textPartType, StringBuilder content) {
        this.textPartType = textPartType;
        parseContent(content);

    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public void testowa() {
        for (TextPart part : children.values()) {
            System.out.println(part.getTitle());
        }
    }


    private void parseContent(StringBuilder content) {

        if (this.textPartType != TextPartType.Root) {
            parseName(content);
            parseTitle(content);
        }


        //Parsuje po następnym typie
        TextPartType type = this.textPartType;
        Matcher m;
        Pattern r;

        //Sprawdz czy to już nie jest ostani rodzaj 'węzła'
        if (type.next() != TextPartType.END) {
            do {
                type = type.next();
                r = Pattern.compile(RegularExpressions.getTextPartRegularExpression(type));
                m = r.matcher(content);
            } while (!m.find() && type.next() != TextPartType.END);

            m.reset();

            List<TextPart> nextParts = new LinkedList<>();

            while (m.find()) {
                nextParts.add(new TextPart(type, new StringBuilder(m.group(0))));
            }

            //Wrzucanie listy do hashmapy -> bo skoro już sparsowane to mamy klucz
            for (TextPart part : nextParts) {
                children.put(part.name, part);
            }

            //Wycinamy podelementy
            content = new StringBuilder(m.replaceAll(""));
        }
        //Zawartość tego 'węzła' to pozostałość po wycinkach
        this.content = content.toString();

    }

    private void parseName(StringBuilder content) {
        Matcher m;
        Pattern r = Pattern.compile(RegularExpressions.getTextPartNameRegularExpression(this.textPartType));
        m = r.matcher(content);

        m.find();
        this.name = m.group(0);

        content.replace(m.start(), m.end(), "");
    }

    private void parseTitle(StringBuilder content) {
        Matcher m;
        Pattern r = Pattern.compile(RegularExpressions.getTextPartTitleRegularExpression(this.textPartType));
        m = r.matcher(content);

        m.find();
        this.title = m.group(0);

        content.replace(m.start(), m.end(), "");
    }

}
