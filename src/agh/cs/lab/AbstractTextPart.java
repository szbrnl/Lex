package agh.cs.lab;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AbstractTextPart {
    protected TextPartType textPartType;

    protected Set<AbstractTextPart> children;

    public List<AbstractTextPart> getAllChildren() {
        return new LinkedList<>(children);
    }

    public TextPartType getTextPartType() {
        return textPartType;
    }

    //TODO zwroc potomka



    //TODO zwroc z przedzialu

    //Spis tesci calosci

    //Spis tresci rozdzialu

    //Spis tresci naglowka

    //Klasa bez tekstu i druga z tekstem ktora z niej dziedziczy -- polimorfizm
    //Nadrzedna jesli ma sie wypisac, nie wypisuje siebie tylko dzieci
    //Dzieci mogą być różnych typów -- różne poziomy tekstu


}
