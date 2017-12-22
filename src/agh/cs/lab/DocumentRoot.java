package agh.cs.lab;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static agh.cs.lab.TextParser.fromRoman;

public class DocumentRoot extends TextPart {

    private Map<String, TextPart> articles = new LinkedHashMap<>();

    public DocumentRoot(StringBuilder content) {
        super(TextPartType.Root, content);
        this.getAllOfType(TextPartType.Article).stream().forEachOrdered(x -> articles.put(x.name, x));

    }

    public String GetElementAsString(TextPartType textPartType, String number) throws IllegalArgumentException {
        TextPart part = searchByKey(textPartType, textPartType.generateKey(number), this);

        if (part == null)
            throw new IllegalArgumentException("Element not found");

        StringBuilder element = part.GetFullElement();
        return element.toString();
    }


    public String getElementAsString(TextPartType[] textPartTypes, String[] numbers) throws IllegalArgumentException {
        TextPart part = this;

        if (textPartTypes.length != numbers.length)
            throw new IllegalArgumentException("Wrong parameters");

        for (int i = 0; i < textPartTypes.length; i++) {
            part = searchByKey(textPartTypes[i], textPartTypes[i].generateKey(numbers[i]), part);
            if (part == null) {
                throw new IllegalArgumentException("Element not found");
            }
        }

        return part.GetFullElement().toString();
    }

    public String getTableOfContents() {
        StringBuilder tableOfContents = new StringBuilder();

        this.getAllAboveEqualsType(TextPartType.Title).stream().forEachOrdered(
                x-> {

                    tableOfContents.append(x.name + "\n");

                    if(x.title.length()>0) {
                        tableOfContents.append(x.title + "\n");
                    }
                    tableOfContents.append("\n");
                }
        );

        return tableOfContents.toString();
    }

    public String getTableOfContents(TextPartType type, String number) throws IllegalArgumentException{

        StringBuilder tableOfContents = new StringBuilder();

        TextPart element =  this.searchByKey(type, type.generateKey(number), this);

        if(element == null)
            throw new IllegalArgumentException("Element not found");

        element.getAllAboveEqualsType(TextPartType.Title).stream().forEachOrdered(
                x-> {
                    tableOfContents.append(x.name + "\n");

                    if(x.title.length()>0)
                        tableOfContents.append(x.title + "\n");
                }
        );

        return tableOfContents.toString();
    }


    public String getArticlesInRange(String start, String end) {
        String startKey = TextPartType.Article.generateKey(start);
        String endKey = TextPartType.Article.generateKey(end);

        if (!articles.containsKey(startKey))
            throw new IllegalArgumentException("Starting element not found");
        if (!articles.containsKey(endKey))
            throw new IllegalArgumentException("Ending element not found");

        StringBuilder content = new StringBuilder();
        Iterator<Map.Entry<String, TextPart>> it = articles.entrySet().iterator();

        Map.Entry<String, TextPart> entry = null;
        while (it.hasNext()) {
            entry = it.next();
            if (entry.getKey().equals(startKey))
                break;
        }

        if(entry == null) {
            throw new IllegalArgumentException("Starting element not found");
        }

        content.append(entry.getValue().GetFullElement());

        while(it.hasNext()) {
            entry = it.next();
            if(!entry.getKey().equals(endKey)) {
                content.append(entry.getValue().GetFullElement());
            }
            else {
                content.append(entry.getValue().GetFullElement());
                break;
            }
        }
        return content.toString();

    }


    public String getElementsInRange(TextPartType textPartType, String start, String end) throws IllegalArgumentException {
        TextPart part = this;

        if (searchByKey(textPartType, textPartType.generateKey(start), this) == null)
            throw new IllegalArgumentException("Starting element not found");
        if (searchByKey(textPartType, textPartType.generateKey(end), this) == null)
            throw new IllegalArgumentException("Ending element not found");

        try {
            int first = numberToInt(start);
            int last = numberToInt(end);

            StringBuilder result = searchInRange(textPartType, first, last, this);

            if (result == null)
                throw new IllegalArgumentException("Something went wrong");

            return result.toString();
        }
        //TODO sprawdzić w wykładzie
        catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    private int numberToInt(String number) throws IllegalArgumentException {
        int num;
        if (number.matches("(.*)(\\d+)(.*)")) {
            Matcher matcher = Pattern.compile("\\d+").matcher(number);
            matcher.find();
            num = Integer.parseInt(matcher.group(0));
        } else if (number.matches(".* [XIV]+")) {
            Matcher matcher = Pattern.compile("[XIV]+").matcher(number);
            matcher.find();
            num = fromRoman(new StringBuilder(matcher.group(0)));
        } else throw new IllegalArgumentException("Wrong parameters");
        return num;
    }

    private StringBuilder searchInRange(TextPartType textPartType, int start, int end, TextPart node) {
        if (node.textPartType == TextPartType.END.previous()) return null;
        //TODO isLarger
        if (node.textPartType.ordinal() > textPartType.ordinal()) return null;

        if (node.textPartType == textPartType && (node.getNumber() >= start && node.getNumber() <= end)) {
            return new StringBuilder(node.GetFullElement());
        }

        Map<String, TextPart> children = node.getAllChildren();
        StringBuilder content = new StringBuilder();

        for (TextPart textPart : children.values()) {
            StringBuilder tmp = searchInRange(textPartType, start, end, textPart);
            if (tmp != null) content.append(tmp);
        }

        return content;
    }


    private TextPart searchByKey(TextPartType textPartType, String key, TextPart node) {
        if (node.textPartType == TextPartType.END.previous()) return null;
        if (node.textPartType.ordinal() > textPartType.ordinal()) return null;


        Map<String, TextPart> children = node.getAllChildren();
        if (children.containsKey(key)) return children.get(key);

        for (TextPart textPart : children.values()) {
            TextPart value = searchByKey(textPartType, key, textPart);
            if (value != null) return value;
        }

        return null;
    }

}
