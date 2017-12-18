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

    public void testowa() {

        for (TextPart part : children) {
            System.out.println(part.textPartType.toString() + part.name + "  " + part.title);
            for(TextPart part1 : part.children) {
                System.out.println(part1.textPartType.toString()+"\t\t"+part1.getName()+" "+part1.getTitle());
               for(TextPart part2 : part1.children) {
                    System.out.println(part2.textPartType.toString()+"\t\t\t"+part2.getName()+" "+part2.getTitle());
                }
            }
        }
    }
    public void testowa1() {

        for (TextPart part : children) {
            if(part.textPartType == TextPartType.Article)
            System.out.println(part.textPartType.toString() + part.name + "  " + part.title);
            for(TextPart part1 : part.children) {
                if(part1.textPartType == TextPartType.Article)
                System.out.println(part1.textPartType.toString()+"\t\t"+part1.getName()+" "+part1.getTitle());
                for(TextPart part2 : part1.children) {
                    if(part2.textPartType == TextPartType.Article)
                    System.out.println(part2.textPartType.toString()+"\t\t\t"+part2.getName()+" "+part2.getTitle());
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
