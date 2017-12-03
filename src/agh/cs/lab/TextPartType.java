package agh.cs.lab;

public enum TextPartType {
    Root, Section, Chapter, END, Article, NumPoint, NumParenthesisPoint, LetterParenthesisPoint;

    private static TextPartType[] textPartTypesValues = TextPartType.values();

    TextPartType next() {
        return textPartTypesValues[this.ordinal()+1];
    }

    TextPartType previous() {
        return textPartTypesValues[this.ordinal()-1];
    }

}
