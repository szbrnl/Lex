package agh.cs.lab;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTests {

    public final String Regex = "((Rozdział|DZIAŁ)( [XIVL]{1,})([\\S\\s]*?)(?=\\2|$))";

    @Test
    public void Number_of_konstytucja_chapters_test() throws IOException {
        // Create a Pattern object
        Pattern r = Pattern.compile(Regex);

        // Now create matcher object.
        Matcher m = r.matcher(getTextFromFile("konstytucja.txt"));

        int count = 0;
        while (m.find()) {
            count++;
        }

        assertEquals(13, count);
    }

    @Test
    public void Number_of_uokik_chapters_test() throws IOException {

        // Create a Pattern object
        Pattern r = Pattern.compile(Regex);

        // Now create matcher object.
        Matcher m = r.matcher(getTextFromFile("uokik.txt"));

        int count = 0;
        while (m.find()) {
            count++;
        }

        assertEquals(10, count);
    }

    private String getTextFromFile(String fileName) throws IOException {
        FileInputStream in = null;
        String text = new String();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                text = text + "\n" + line;

            }
        }
        finally {
            //TODO
        }
        return text;
    }


}
