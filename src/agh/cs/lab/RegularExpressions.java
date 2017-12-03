package agh.cs.lab;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.soap.Text;
import java.util.HashMap;
import java.util.Map;

public class RegularExpressions {

    private static Map<TextPartType, String> regularExpressions = new HashMap<>();
    static{
        regularExpressions.put(TextPartType.Section, "((DZIAŁ)( [XIVLA]{1,})([\\S\\s]*?)(?=DZIAŁ|$))");
        regularExpressions.put(TextPartType.Chapter, "((Rozdział)(( [XIVL]{1,})|( \\d{1,}))([\\S\\s]*?)(?=Rozdział|$))");
    }

    private static Map<TextPartType, String> nameRegularExpressions = new HashMap<>();
    static{
        nameRegularExpressions.put(TextPartType.Root, "");
        nameRegularExpressions.put(TextPartType.Section, "(DZIAŁ)( [XIVLA]{1,})");
        nameRegularExpressions.put(TextPartType.Chapter, "(Rozdział)(( [XIVL]{1,})|( \\d{1,}))");
    }

    private static Map<TextPartType, String> titleRegularExpressions = new HashMap<>();
    static{
        titleRegularExpressions.put(TextPartType.Root, "");
        titleRegularExpressions.put(TextPartType.Section, ".{1,}");
        titleRegularExpressions.put(TextPartType.Chapter, ".{1,}");
    }

    public static String getTextPartRegularExpression(TextPartType textPartType){
        return regularExpressions.get(textPartType);
    }

    public static String getTextPartNameRegularExpression(TextPartType textPartType) {
        return nameRegularExpressions.get(textPartType);
    }

    public static String getTextPartTitleRegularExpression(TextPartType textPartType) {
        return titleRegularExpressions.get(textPartType);
    }

}
