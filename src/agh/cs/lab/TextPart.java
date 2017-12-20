package agh.cs.lab;

import java.util.*;

public class TextPart {

    protected final String name;    //np ROZDZIAL III
    protected final String title;   //np. ŹRÓDŁA PRAWA
    protected final List<String> content; //to co zostało - np preambuła

    protected TextPartType textPartType;

    protected LinkedHashMap<String, TextPart> children;

    public LinkedHashMap<String, TextPart> getAllChildren() {
        return new LinkedHashMap<>(children);
    }

    public TextPartType getTextPartType() {
        return textPartType;
    }


    public TextPart(TextPartType textPartType, StringBuilder content) {
        this.textPartType = textPartType;

        TextParser textParser = new TextParser(this.textPartType, content);
        this.children = textParser.getAllChildren();
        this.name = textParser.getName();
        this.title = textParser.getTitle();
        this.content = textParser.getContent();
    }

    public List<String> getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public StringBuilder GetFullElement() {
        StringBuilder elem = new StringBuilder();

        elem.append(name);
        if(textPartType == TextPartType.Chapter || textPartType == TextPartType.Section || textPartType == TextPartType.Article) {
            elem.append('\n');
        }
        if (title.length() > 0) {
            elem.append(title);
            elem.append("\n");
        }

        if(textPartType == TextPartType.Chapter || textPartType == TextPartType.Section || textPartType == TextPartType.Title)
            elem.append("\n");

        if(content.size() > 0) {
            content.stream().forEachOrdered(s-> elem.append(s+"\n"));
            elem.deleteCharAt(elem.length()-1);
        }


        children.values().stream().forEachOrdered(c -> elem.append(c.GetFullElement()));
        if(textPartType.ordinal() <= TextPartType.Article.ordinal())
            elem.append("\n");
        return elem;
    }

    public void testowa1() {

        for (Map.Entry mPart : children.entrySet()) {
            TextPart part = (TextPart) mPart.getValue();
            System.out.println(part.textPartType.toString() + part.name + "  " + part.title);

            for (Map.Entry mPart1 : part.children.entrySet()) {
                TextPart part1 = (TextPart) mPart1.getValue();
                System.out.println(part1.textPartType.toString() + part1.name + "  " + part1.title);

                for (Map.Entry mPart2 : part1.children.entrySet()) {
                    TextPart part2 = (TextPart) mPart2.getValue();
                    System.out.println(part2.textPartType.toString() + part2.name + "  " + part2.title);

                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextPart textPart = (TextPart) o;
        return Objects.equals(name, textPart.name) &&
                textPartType == textPart.textPartType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, textPartType);
    }
}
