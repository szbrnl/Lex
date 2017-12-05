package agh.cs.lab;

import java.io.*;

public class Class1 {
   public static void main(String args[]) throws IOException{

       FileInputStream in = null;
       FileOutputStream out = null;
       String text = new String();
       try (BufferedReader br = new BufferedReader(new FileReader("E:\\konstytucja.txt"))) {
           String line;
           while ((line = br.readLine()) != null) {
              text = text  + "\n" + line;

           }
       }

       //TODO Content nie moze być stringiem -> jakaś lista wierszy, uprzednio pozbawiona znaków nowej linii i myślników na końcach?

       //TODO Usuwanie śmieci z tekstu

       //TODO Osobna klasa parser?

       //TODO Pozostałe regexy


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
