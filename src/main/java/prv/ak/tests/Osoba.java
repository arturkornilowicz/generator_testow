package prv.ak.tests;

import prv.ak.testable.*;

import java.time.LocalDate;

public class Osoba {

    private String imie;
    private String nazwisko;
    private char plec;

    private Integer rok;

    public Osoba() {
    }

    @TestableConstructor(args = {"Jan","Kowal","2000","M"})
    public Osoba(String imie, String nazwisko, Integer rok, char plec) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.rok = rok;
        this.plec = plec;
    }

    @TestableMethod(expectedValue = "Jan")
    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    @TestableMethod(expectedValue = "Kowal")
    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Integer getRok() {
        return rok;
    }

    public void setRok(Integer rok) {
        this.rok = rok;
    }

    @TestableMethod(
            expectedValue = "21",
            args = {"2021"}
    )
    public int wiek(int rok) {
        return rok - this.rok;
    }

    @TestableMethod(
            expectedValue = "24"
    )
    public int wiek() {
        return wiek(LocalDate.now().getYear());
    }

    @TestableMethod(
            expectedValue = "M"
    )
    public char getPlec() {
        return plec;
    }

}
