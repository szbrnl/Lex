package agh.cs.lab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextPartTypeTest {

    @Test
    void next() {
        TextPartType type = TextPartType.Root;
        assertEquals(TextPartType.Section, type.next());

        type = type.next();
        assertEquals(TextPartType.Chapter, type.next());
    }

    @Test
    void previous() {
    }

    @Test
    void isBelow() {
        assertTrue(TextPartType.Article.isBelow(TextPartType.Chapter));
        assertTrue(TextPartType.Article.isBelow(TextPartType.Section));
        assertTrue(TextPartType.Paragraph.isBelow(TextPartType.Article));
        assertTrue(TextPartType.Paragraph.isBelow(TextPartType.Chapter));
        assertFalse(TextPartType.Article.isBelow(TextPartType.Paragraph));
    }

    @Test
    void generateKey() {
        TextPartType type = TextPartType.Chapter;
        assertEquals("Rozdział 2", type.generateKey("2"));
    }

    @Test
    void getTextPartRegularExpression() {
    }

    @Test
    void getTextPartNameRegularExpression() {
        TextPartType type = TextPartType.Root;
        assertEquals("", type.getTextPartNameRegularExpression());

        type = TextPartType.Section;
        assertEquals("(DZIAŁ)( [XIVLA]{1,})", type.getTextPartNameRegularExpression());

        type = TextPartType.Chapter;
        assertEquals("(Rozdział)(( [XIVL]{1,})|( \\d{1,}[a-z]{0,}))", type.getTextPartNameRegularExpression());
    }

    @Test
    void getTextPartTitleRegularExpression() {
        TextPartType type = TextPartType.Root;
        assertEquals("", type.getTextPartTitleRegularExpression());

        type = TextPartType.Section;
        assertEquals(".{1,}", type.getTextPartTitleRegularExpression());

        type = TextPartType.Chapter;
        assertEquals("((?=\\p{Lu}{1}[a-z])([\\S\\s]+?)(?=Art\\.))|((?<!\\p{Lu}{1}[a-z])([\\p{Lu} ,]){2,}?(?=\\n))", type.getTextPartTitleRegularExpression());
    }
}