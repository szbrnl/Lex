package agh.cs.lab;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public enum TextPartType {
    Root, Section, Chapter, Title, Article, Paragraph, NumParenthesisPoint, LetterParenthesisPoint, Anything,  END;

    TextPartType next() {
        return (TextPartType.values())[this.ordinal() + 1];
    }

    TextPartType previous() {
        return (TextPartType.values())[this.ordinal() - 1];
    }

    public boolean isBelow(TextPartType other) {
        return this.ordinal() > other.ordinal();
    }

    public boolean isAbove(TextPartType other) {
        return this.ordinal() < other.ordinal();
    }

    public String generateKey(String number) {
        switch (this) {
            case Section:

            case Chapter:
                return "Rozdział "+number;

            case Article:
                return "Art. " + number + ".";

            case Paragraph:
                return number+". ";

            case NumParenthesisPoint:

            case LetterParenthesisPoint:

            default:
                throw new NotImplementedException();


        }
    }

    public String getTextPartRegularExpression() {
        return regularExpressions.get(this.ordinal());
    }

    public String getTextPartNameRegularExpression() {
        return nameRegularExpressions.get(this.ordinal());
    }

    public String getTextPartTitleRegularExpression() {
        return titleRegularExpressions.get(this.ordinal());
    }

    private List<String> regularExpressions = Arrays.asList(
            "",
            "((DZIAŁ)( [XIVLA]{1,})([\\S\\s]*?)(?=DZIAŁ|$))",
            "((Rozdział)(( [XIVL]{1,})|( \\d{1,}))([\\S\\s]*?)(?=Rozdział|$))",
            "(?m)^([\\p{Lu} ,]+){2,}(\\n)([\\S\\s]*?)(?=\\p{Lu}{3,}|\\Z)",
            "(Art\\. \\d*[a-z]*\\.)([\\s\\S]*?)(?=Art\\. \\d+[a-z]*\\.|$)",
            "(\\d{1,}\\. )([\\s\\S]*?)(?=(\\n\\d{1,}\\. |$))",
            "(\\d{1,}[a-z]*\\) )([\\s\\S]*?)(?=(\\d{1,}[a-z]*\\) |$))",
            "([a-z]{1,}\\) )([\\s\\S]*?)(?=([a-z]{1,}\\) |$))",
            ""

    );
    private List<String> titleRegularExpressions = Arrays.asList(
            "",
            ".{1,}",
            "((?=\\p{Lu}{1}[a-z])([\\S\\s]+?)(?=Art\\.))|((?<!\\p{Lu}{1}[a-z])([\\p{Lu} ,]){2,}?(?=\\n))",
            "",
            "",
            "",
            "",
            "",
            ""
    );
    private List<String> nameRegularExpressions = Arrays.asList(
            "",
            "(DZIAŁ)( [XIVLA]{1,})",
            "(Rozdział)(( [XIVL]{1,})|( \\d{1,}[a-z]{0,}))",
            "[(\\p{Lu}) ,]+(?=\\n|$)",
            "(Art\\. \\d+[a-z]*\\.)",
            "(\\d{1,}\\. )",
            "(\\d{1,}[a-z]{0,}\\) )",
            "([a-z]{1,}\\) )",
            ""

    );
}
