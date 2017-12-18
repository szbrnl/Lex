package agh.cs.lab;

import javax.xml.soap.Text;

public class TextPartContainer extends AbstractTextPart {

    public TextPartContainer(TextPartType pretendedTextPartType, StringBuilder content) {
        this.textPartType = TextPartType.Container;
    }

}
