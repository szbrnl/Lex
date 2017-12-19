package agh.cs.lab;

import java.util.*;

public class TextPart extends AbstractTextPart {

    private final String name;    //np ROZDZIAL III
    private final String title;   //np. ŹRÓDŁA PRAWA
    private final String content; //to co zostało - np preambuła


    public TextPart(TextPartType textPartType, StringBuilder content) {
        this.textPartType = textPartType;

        TextParser textParser = new TextParser(this.textPartType, content);
        this.children = textParser.getChildren();
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

        for (AbstractTextPart part : children) {
            if(part instanceof TextPart) {
                TextPart textPart = (TextPart) part;
                System.out.println(textPart.textPartType.toString() + textPart.name + "  " + textPart.title);
            }
            for(AbstractTextPart part1 : part.children) {
                if(part1 instanceof TextPart) {
                    TextPart textPart1 = (TextPart) part1;
                    System.out.println(textPart1.textPartType.toString() + textPart1.name + "  " + textPart1.title);
                }
                for(AbstractTextPart part2 : part1.children) {
                    if(part2 instanceof TextPart) {
                        TextPart textPart2 = (TextPart) part2;
                        System.out.println(textPart2.textPartType.toString() + textPart2.name + "  " + textPart2.title);
                    }
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextPart textPart = (TextPart) o;
        return textPartType == textPart.textPartType &&
                Objects.equals(name, textPart.name) &&
                Objects.equals(title, textPart.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textPartType, name, title);
    }
}
