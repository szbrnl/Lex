package agh.cs.lab;

import java.io.*;

public class Class1 {
   public static void main(String args[]) throws IOException{

       StringBuilder text = new StringBuilder();
       try (BufferedReader br = new BufferedReader(new FileReader("uokik.txt"))) {
           String line;
           while ((line = br.readLine()) != null) {
              text.append(line);
              text.append("\n");
           }
       }

       //TODO pominięte wyciąć

       //TODO Content nie moze być stringiem -> jakaś lista wierszy, uprzednio pozbawiona znaków nowej linii i myślników na końcach?

       //TODO Usuwanie śmieci z tekstu

       //TODO Osobna klasa parser?

       //TODO Pozostałe regexy

       //TODO regularexpression nie ma być statyczne

       //TODO regularexpressions do ENUMA

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

       TextPart textPart = new TextPart(TextPartType.Root, text);
       textPart.testowa();
   }
}
