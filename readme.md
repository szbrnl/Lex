Zadamie 1 - Programowanie Obiektowe (semestr III)
Zamiana pliku .txt na formę obiektową. Program parsuje pliki konstytucja.txt/uokik.txt i pozwala uzyskać dostęp do poszczególnych elementów składowych danego pliku (artykuły, punkty, ustępy). Dodatkowo posiada możliwość wyświetlenia spisu treści danej ustawy (bądź danego elementu składowego).

Oryginalne polecenie w pliku polecenie.md.

Instrukcja obsługi:

```javac -encoding UTF8 ... (na Windows)```

1. Pomoc
<br> ```lex -h```
2. Wczytywanie plików
<br>```lex -f <nazwa_pliku> <inne_opcje>```
3. Art. 1., ust. 1: "Ustawa określa warunki..."
<br>```lex -f uokik.txt -c -A 1 -P 1```
<br>gdzie ```-c``` -> wyświetlanie treści, ```-A 1``` -> wyświetlanie artykułu o konretnym numerze
<br>```-P```-> wyświetlanie ustępu o konkretnym numerze
4. Art. 2, ust. 2., pkt 1)
<br> ```lex -f uokik.txt -c -A 2 -P 2 -Po 2```
5. Art. 2, ust. 2., pkt 2), lit. a)
<br> ```lex -f uokik.txt -c -A 2 -P 2 -Po 2 - LPo 2```
6. Spis treści rozdziału o określonym numerze
<br> ```lex -f konstytucja.txt -tc -C II```
<br> analogicznie dla działu:
<br>```lex -f konstytucja.txt -tc -S 2```
7. Spis treści całej ustawy:
<br>```lex -f konstytucja.txt -tc```
 
