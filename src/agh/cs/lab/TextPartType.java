package agh.cs.lab;

import java.util.*;

public enum TextPartType {
    Root, Section, Chapter, CapitalLetters, END, Article, NumPoint, NumParenthesisPoint, LetterParenthesisPoint;

    TextPartType next() {
        return (TextPartType.values())[this.ordinal() + 1];
    }

    TextPartType previous() {
        return (TextPartType.values())[this.ordinal() - 1];
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
            "([\\p{Lu} ]{1,})(\\n)([\\S\\s]*?)(?=\\b[A-Z]{2,}\\b)"
    );
    private List<String> titleRegularExpressions = Arrays.asList(
            "",
            ".{1,}",
            ".{1,}",
            "([\\\\p{Lu} ]{1,})(?=\\\\n)"
    );
    private List<String> nameRegularExpressions = Arrays.asList(
            "",
            "(DZIAŁ)( [XIVLA]{1,})",
            "(Rozdział)(( [XIVL]{1,})|( \\d{1,}[a-z]{0,}))",
            ""

    );
}
