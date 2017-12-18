package agh.cs.lab;

import javax.xml.soap.Text;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextPart {
    public final TextPartType textPartType;

    private String name;    //np ROZDZIAL III
    private String title;   //np. ŹRÓDŁA PRAWA
    private String content; //to co zostało - np preambuła

    private Set<TextPart> children = new LinkedHashSet<>();


    public TextPart(TextPartType textPartType, StringBuilder content) {
        this.textPartType = textPartType;
        parseContent(content);
    }

    public TextPart(TextPartType type, StringBuilder name, StringBuilder title, StringBuilder content) {
        this.textPartType =type;
        this.name = name.toString();
        this.title = title.toString();
        //this.content = parseContent(content);
        //Jako rozdzielenie contentu na kolejne linijki
    }

    public void SetChildren(Set<TextPart> children) {
        this.children = children;
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

        for (TextPart part : children) {
            System.out.println(part.name + "  " + part.title);
            for(TextPart part1 : part.children) {
                System.out.println("\t\t"+part1.getName()+" "+part1.getTitle());
            }
        }
    }


    private void parseContent(StringBuilder content) {

        if (this.textPartType != TextPartType.Root) {

            try {
                parseName(content);
            }
            catch(IllegalStateException ex) {
                //Could not find name, so it does not exist
                this.name = "";
            }

            try {
                parseTitle(content);
            }
            catch (IllegalStateException ex) {
                //Could not find title, so it does not exist
                this.title = "";
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
                m = r.matcher(content);
            } while (!m.find() && type.next() != TextPartType.END);

            m.reset();

            List<TextPart> nextParts = new LinkedList<>();

            while (m.find()) {
                nextParts.add(new TextPart(type, new StringBuilder(m.group(0))));
            }

            //Wrzucanie listy do hashmapy -> bo skoro już sparsowane to mamy klucz
            children.addAll(nextParts);

            //Wycinamy podelementy
            content = new StringBuilder(m.replaceAll(""));
        }
        //Zawartość tego 'węzła' to pozostałość po wycinkach
        this.content = content.toString();

    }

    private void parseName(StringBuilder content) {
        Matcher m;
        Pattern r = Pattern.compile(textPartType.getTextPartNameRegularExpression());
        m = r.matcher(content);

        m.find();
        this.name = m.group(0);

        content.replace(m.start(), m.end(), "");
    }

    private void parseTitle(StringBuilder content) throws IllegalStateException {
        Matcher m;
        Pattern r = Pattern.compile(textPartType.getTextPartTitleRegularExpression());
        m = r.matcher(content);

        m.find();
        this.title = m.group(0);

        content.replace(m.start(), m.end(), "");
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextPart textPart = (TextPart) o;
        return textPartType == textPart.textPartType &&
                Objects.equals(name, textPart.name) &&
                Objects.equals(title, textPart.title) &&
                Objects.equals(content, textPart.content) &&
                Objects.equals(children, textPart.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textPartType, name, title, content, children);
    }
}
