package agh.cs.lab;

import com.sun.xml.internal.bind.v2.TODO;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.soap.Text;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextPart {
    public final TextPartType textPartType;

    private String name;    //np ROZDZIAL III
    private String title;   //np. ŹRÓDŁA PRAWA
    private String content;

    private Map<String, TextPart> children = new LinkedHashMap<>();


    public TextPart(TextPartType textPartType, String content) {
        this.textPartType = textPartType;
        parseContent(content);

    }

    public String getContent() {
        return content;
    }

    private void parseContent(String content) {
        //Wycina typ, numer i tytuł
        //TODO
        //Bez wycinania nazwy nie mamy hasha do rozróżniania elementów w hashset



        //Parsuje po następnym typie
        TextPartType type = this.textPartType;
        Matcher m;

        if(type != TextPartType.Chapter) {
            do{
                type = type.next();
                Pattern r = Pattern.compile(RegularExpressions.getTextPartRegularExpression(type));
                m = r.matcher(content);
            }while (!m.find() && type != TextPartType.Article);

            m.reset();

            List<TextPart> nextParts = new LinkedList<>();

            while (m.find()) {
                nextParts.add(new TextPart(type, m.group(0)));
            }

            //Wrzucanie listy do hashmapy -> bo skoro już sparsowane to mamy klucz

            for(TextPart part : nextParts) {
                children.put(part.name, part);
            }

            //Wycinamy podelementy
            content = m.replaceAll("");
        }
        //Zawartość tego 'węzła' to pozostałość po wycinkach
        this.content = content;

        for(Object part : children.values().toArray()) {
            TextPart tPart = (TextPart) part;
            System.out.println(tPart.getContent());
        }


    }

}
