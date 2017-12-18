package agh.cs.lab;

import java.util.*;

public enum TextPartType {
    Container, Root, Section, Chapter, CapitalLetters, Article, END, NumPoint, NumParenthesisPoint, LetterParenthesisPoint;

    TextPartType next() {
        return (TextPartType.values())[this.ordinal() + 1];
    }

    TextPartType previous() {
        return (TextPartType.values())[this.ordinal() - 1];
    }

    public String getTextPartRegularExpression() {
        return regularExpressions.get(this.ordinal()-1);
    }

    public String getTextPartNameRegularExpression() {
        return nameRegularExpressions.get(this.ordinal()-1);
    }

    public String getTextPartTitleRegularExpression() {
        return titleRegularExpressions.get(this.ordinal()-1);
    }

    private List<String> regularExpressions = Arrays.asList(
            "",
            "((DZIAŁ)( [XIVLA]{1,})([\\S\\s]*?)(?=DZIAŁ|$))",
            "((Rozdział)(( [XIVL]{1,})|( \\d{1,}))([\\S\\s]*?)(?=Rozdział|$))",
            "(?m)^([\\p{Lu} ,]+){2,}(\\n)([\\S\\s]*?)(?=\\p{Lu}{3,}|\\Z)",
            //"(((\\p{Lu}){1,}[ ,]?){1,})(\\n)([\\S\\s]*?)(?=\\b\\p{Lu}{2,}\\b|$)",
            "(Art\\. \\d*[a-z]*\\.)([\\s\\S]*?)(?=Art\\. \\d+[a-z]*\\.|$)"

    );
    private List<String> titleRegularExpressions = Arrays.asList(
            "",
            ".{1,}",
            ".{1,}",
            "",
            ""
    );
    private List<String> nameRegularExpressions = Arrays.asList(
            "",
            "(DZIAŁ)( [XIVLA]{1,})",
            "(Rozdział)(( [XIVL]{1,})|( \\d{1,}[a-z]{0,}))",
            "[(\\p{Lu}) ,]+(?=\\n|$)",
            "(Art\\. \\d+[a-z]*\\.)"

    );
}
