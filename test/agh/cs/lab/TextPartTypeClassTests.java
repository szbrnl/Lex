package agh.cs.lab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextPartTypeClassTests {

    @Test
    public void GetTextPartTitleRegularExpressionTest() {
        TextPartType type = TextPartType.Root;
        assertEquals("", type.getTextPartTitleRegularExpression());

        type = TextPartType.Section;
        assertEquals(".{1,}", type.getTextPartTitleRegularExpression());

        type = TextPartType.Chapter;
        assertEquals(".{1,}", type.getTextPartTitleRegularExpression());
    }

    @Test
    public void GetTextPartNamerRegularExpressionTest() {
        TextPartType type = TextPartType.Root;
        assertEquals("", type.getTextPartNameRegularExpression());

        type = TextPartType.Section;
        assertEquals("(DZIAŁ)( [XIVLA]{1,})", type.getTextPartNameRegularExpression());

        type = TextPartType.Chapter;
        assertEquals("(Rozdział)(( [XIVL]{1,})|( \\d{1,}[a-z]{0,}))", type.getTextPartNameRegularExpression());
    }

    @Test
    public void TextPartTypeNextTest() {
        TextPartType type = TextPartType.Root;
        assertEquals(TextPartType.Section, type.next());

        type = type.next();
        assertEquals(TextPartType.Chapter, type.next());

        type = type.next();
        //assertEquals(TextPartType.CapitalLetters, type.next());
    }

}
