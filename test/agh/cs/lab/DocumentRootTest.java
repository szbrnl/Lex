package agh.cs.lab;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DocumentRootTest {

    @Test
    void getElementAsString() {

        try {
            StringBuilder text = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader("uokik.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append("\n");
                }
            }

            DocumentRoot root = new DocumentRoot(text);
            try {
                StringBuilder sb = new StringBuilder(root.GetElementAsString(new TextPartType[]{TextPartType.Chapter},
                        new String[]{"4"}));

                String expected = new String("Rozdział 4\n" +
                        "Postępowanie w sprawach praktyk naruszających zbiorowe interesy\n" +
                        "konsumentów\n" +
                        "\n" +
                        "\n" +
                        "Art. 100.\n" +
                        "1. Każdy może zgłosić Prezesowi Urzędu na piśmie zawiadomienie\n" +
                        "dotyczące podejrzenia stosowania praktyk naruszających zbiorowe interesy\n" +
                        "konsumentów.\n" +
                        "2. Zawiadomienie, o którym mowa w ust. 1, może także zgłosić zagraniczna\n" +
                        "organizacja wpisana na listę organizacji uprawnionych w państwach członkowskich\n" +
                        "Unii Europejskiej do złożenia wniosku o wszczęcie postępowania, opublikowaną\n" +
                        "w Dzienniku Urzędowym Wspólnot Europejskich, jeżeli cel jej działania uzasadnia\n" +
                        "zgłoszenie przez nią zawiadomienia dotyczącego naruszenia wynikającego\n" +
                        "z niezgodnych z prawem zaniechań lub działań podjętych na terytorium\n" +
                        "Rzeczypospolitej Polskiej, zagrażających zbiorowym interesom konsumentów\n" +
                        "w państwie członkowskim, w którym organizacja ta ma swoją siedzibę.\n" +
                        "3. Przepisy art. 86 ust. 2–4 stosuje się odpowiednio.\n" +
                        "\n" +
                        "Art. 101.\n" +
                        "1. Stroną postępowania jest każdy, wobec kogo zostało wszczęte\n" +
                        "postępowanie w sprawie praktyk naruszających zbiorowe interesy konsumentów.\n" +
                        "2. Prezes Urzędu wydaje postanowienie o wszczęciu postępowania w sprawie\n" +
                        "praktyk naruszających zbiorowe interesy konsumentów i zawiadamia o tym strony.\n" +
                        "\n" +
                        "Art. 101a.\n" +
                        "1. Przepis ust. 2 zdanie drugie stosuje się odpowiednio.\n" +
                        "2. W decyzji, o której mowa w ust. 1, Prezes Urzędu określa czas jej\n" +
                        "obowiązywania. Decyzja ta obowiązuje nie dłużej niż do czasu wydania decyzji\n" +
                        "kończącej postępowanie w sprawie.\n" +
                        "©Kancelaria Sejmu s. 50/73\n" +
                        "2017-06-28\n" +
                        "3. Prezes Urzędu może przedłużyć, w drodze decyzji, czas obowiązywania\n" +
                        "decyzji, o której mowa w ust.\n" +
                        "4. W decyzji, o której mowa w ust. 1, nie nakłada się kary, o której mowa w art.\n" +
                        "106 ust. 1 pkt 4.\n" +
                        "5. Przepisy art. 89 ust. 5 i 6 stosuje się odpowiednio.\n" +
                        "\n" +
                        "Art. 102.\n" +
                        "(uchylony)\n" +
                        "\n" +
                        "Art. 103.\n" +
                        "Prezes Urzędu może nadać decyzji w całości lub w części rygor\n" +
                        "natychmiastowej wykonalności, jeżeli wymaga tego ważny interes konsumentów.\n" +
                        "\n" +
                        "Art. 104.\n" +
                        "Postępowanie w sprawie praktyk naruszających zbiorowe interesy\n" +
                        "konsumentów powinno być zakończone w terminie 4 miesięcy, a w sprawie\n" +
                        "szczególnie skomplikowanej – nie później niż w terminie 5 miesięcy od dnia jego\n" +
                        "wszczęcia. Przepisy art. 35–38 ustawy z dnia 14 czerwca 1960 r. – Kodeks\n" +
                        "postępowania administracyjnego stosuje się odpowiednio.\n" +
                        "\n" +
                        "Art. 105.\n" +
                        "Nie wszczyna się postępowania w sprawie praktyk naruszających\n" +
                        "zbiorowe interesy konsumentów, jeżeli od końca roku, w którym zaprzestano ich\n" +
                        "stosowania, upłynęły 3 lata.\n\n\n");

                assertEquals(expected, sb.toString());

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }


        } catch(IOException e) {
            fail("Could not open file");
        }

    }
}