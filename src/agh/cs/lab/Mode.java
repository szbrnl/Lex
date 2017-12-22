package agh.cs.lab;

public enum Mode {
    Help, OpenFile, TableOfContents, Content, Full, Section, Chapter, Title, Article, Articles, Paragraph, Point, LetterPoint, Preamble;

    public TextPartType toTextPartType() {
        switch(this) {
            case Chapter:
                return TextPartType.Chapter;
            case Section:
                return TextPartType.Section;
            case Article:
                return TextPartType.Article;
            case Paragraph:
                return TextPartType.Paragraph;
            case Point:
                return TextPartType.NumParenthesisPoint;
            case LetterPoint:
                return TextPartType.LetterParenthesisPoint;
            default:
                throw new IllegalArgumentException("Illegal argument");
        }
    }
}
