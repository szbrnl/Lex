package agh.cs.lab;

import java.util.*;

public class TextPart {

    private final String name;    //np ROZDZIAL III
    private final String title;   //np. ŹRÓDŁA PRAWA
    private final String content; //to co zostało - np preambuła
    //private final int number;

    protected TextPartType textPartType;

    protected LinkedHashMap<String, TextPart> children;

    public List<TextPart> getAllChildren() {
        return new LinkedList<>(children.values());
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

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public void testowa1() {

        for (Map.Entry mPart : children.entrySet()) {
            TextPart part = (TextPart) mPart.getValue();
                System.out.println(part.textPartType.toString() + part.name + "  " + part.title);

            for(Map.Entry mPart1 : part.children.entrySet()) {
                    TextPart part1 = (TextPart) mPart1.getValue();
                    System.out.println(part1.textPartType.toString() + part1.name + "  " + part1.title);

                for(Map.Entry mPart2 : part1.children.entrySet()) {
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
