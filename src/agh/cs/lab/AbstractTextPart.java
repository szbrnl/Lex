package agh.cs.lab;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AbstractTextPart {
    protected TextPartType textPartType;

    protected Set<TextPart> children;

    public List<TextPart> getAllChildren() {
        return new LinkedList<>(children);
    }

    public TextPartType getTextPartType() {
        return textPartType;
    }


}
