package agh.cs.lab;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Class1 {
   public static void main(String args[]) throws IOException{

       FileInputStream in = null;
       FileOutputStream out = null;
       String text = new String();
       try (BufferedReader br = new BufferedReader(new FileReader("E:\\uokik.txt"))) {
           String line;
           while ((line = br.readLine()) != null) {
              text = text  + "\n" + line;

           }
       }

//       Writer writer = new BufferedWriter(new OutputStreamWriter(
//               new FileOutputStream("filename.txt"), "utf-8"));
//       writer.write(text);
//
//       // Create a Pattern object
//       Pattern r = Pattern.compile("((Rozdział|DZIAŁ)( [XIVL]{1,})([\\S\\s]*?)(?=\\2|$))");
//
//       // Now create matcher object.
//       Matcher m = r.matcher(text);
//
//
//       while(m.find()) {
//           System.out.println(m.group(0));
//           System.out.println("------");
//       }

       TextPart textPart = new TextPart(TextPartType.Root, new StringBuilder(text));
       textPart.testowa();
   }
}
