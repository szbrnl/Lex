package agh.cs.lab;

public class TextPartContainer extends AbstractTextPart {

    public TextPartContainer(TextPartType pretendedTextPartType, StringBuilder content) {
        textPartType = pretendedTextPartType;

        TextParser textParser = new TextParser(this.textPartType, content);
        this.children = textParser.getChildren();
    }

}
