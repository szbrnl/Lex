package agh.cs.lab;

import java.util.Map;

public class DocumentRoot extends TextPart {

    public DocumentRoot(StringBuilder content) {
        super(TextPartType.Root, content);
    }

    public String GetElementAsString(TextPartType textPartType, String number) throws IllegalArgumentException {
        TextPart part = search(textPartType, textPartType.generateKey(number), this);

        if(part == null)
            throw new IllegalArgumentException("Element not found");

        StringBuilder element = part.GetFullElement();
        return element.toString();
    }

    public String GetElementAsString(TextPartType[] textPartTypes, String[] numbers) {
        TextPart part = this;

        if(textPartTypes.length != numbers.length)
            throw new IllegalArgumentException("Wrong parameters");

        for(int i=0; i<textPartTypes.length; i++) {
            part = search(textPartTypes[i], textPartTypes[i].generateKey(numbers[i]), part);
            if(part == null) {
                throw new IllegalArgumentException("Element not found");
            }
        }

        return part.GetFullElement().toString();
    }


    private TextPart search(TextPartType textPartType, String key, TextPart node) {
        if(node.textPartType == TextPartType.END.previous()) return null;
        if(node.textPartType.ordinal() > textPartType.ordinal()) return null;


        Map<String, TextPart> children = node.getAllChildren();
        if(children.containsKey(key)) return children.get(key);

        for(TextPart textPart : children.values()){
            TextPart value = search(textPartType, key, textPart);
            if(value != null) return value;
        }

        return null;
    }

}
