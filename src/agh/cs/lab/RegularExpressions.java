package agh.cs.lab;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.soap.Text;
import java.util.HashMap;
import java.util.Map;

public class RegularExpressions {

    private static Map<TextPartType, String> regularExpressions = new HashMap<>();
    static{
        regularExpressions.put(TextPartType.Section, "((DZIAŁ)( [XIVL]{1,})([\\S\\s]*?)(?=DZIAŁ|$))");
        regularExpressions.put(TextPartType.Chapter, "((Rozdział)( [XIVL]{1,})([\\S\\s]*?)(?=Rozdział|$))");
    }

    public static String getTextPartRegularExpression(TextPartType textPartType){
        return regularExpressions.get(textPartType);
    }

    public static String getTextPartTitleRegularExpression(TextPartType textPartType) {
        throw new NotImplementedException();
    }

}
