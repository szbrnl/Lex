package agh.cs.lab;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextParserTests {

    @Test
    public void NumberOfTextPartUokikTest() throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("uokik.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        }

        AbstractTextPart textPart = new TextPart(TextPartType.Root, text);
        List<AbstractTextPart> children = textPart.getAllChildren();

        int a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Section, textPart);
        assertEquals(10, a);

        a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Chapter, textPart);
        assertEquals(19, a);

        a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.CapitalLetters, textPart);
        assertEquals(0, a);

        a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Article, textPart);
        assertEquals(175, a);
    }

    @Test
    public void NumberOfTextPartKonstytucjaTest() throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("konstytucja.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        }

        AbstractTextPart textPart = new TextPart(TextPartType.Root, text);
        List<AbstractTextPart> children = textPart.getAllChildren();

        int a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Section, textPart);
        assertEquals(0, a);

        a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Chapter, textPart);
        assertEquals(13, a);

        a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.CapitalLetters, textPart);
        assertEquals(16, a);

        a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Article, textPart);
        assertEquals(243, a);
    }


    private int countAllTextPartsWithSpecifiedTextPartType(TextPartType textPartType, AbstractTextPart root) {
        if (textPartType == TextPartType.END) return 0;

        int a = 0;
        for (AbstractTextPart textPart : root.getAllChildren()) {
            a += countAllTextPartsWithSpecifiedTextPartType(textPartType, textPart);
        }
        if (root.getTextPartType() == textPartType && root instanceof TextPart) a += 1;

        return a;
    }

}
