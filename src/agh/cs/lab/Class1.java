package agh.cs.lab;

import java.io.*;

public class Class1 {
    public static void main(String args[]) throws IOException {

        StringBuilder text = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("konstytucja.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        }

        //TODO przerobić zwracanie tekstu pry pomocy metody na toString?

        //TODO uokik art 4

        //TODO pominięte wyciąć (pamiętać o przedziale w uokik)

        //TODO Usuwanie śmieci z tekstu

        DocumentRoot root = new DocumentRoot(text);
        //root.testowa1();

//       try {
//           System.out.print(root.GetElementAsString(TextPartType.Chapter, "5"));
//       } catch (IllegalArgumentException e) {
//           System.out.println("Nie znaleziono takiego elemenu w podanym pliku");
//       }


        try {
            System.out.print(root.GetElementAsString(new TextPartType[]{TextPartType.Chapter},
                    new String[]{"II"}));
        } catch (IllegalArgumentException e) {
            System.out.println("Nie znaleziono takiego elemenu w podanym pliku");
        }

    }
}
