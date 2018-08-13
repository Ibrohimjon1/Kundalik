package com.example.ibrohimjon.kundalik;


public class Qarzdorlar_list {

    String tanish_ismi, bosh_harf, summa, sana;
    int id;

    public Qarzdorlar_list(String tanish_ismi, String summa, String bosh_harf, String sana, int id) {
        this.tanish_ismi = tanish_ismi;
        this.summa = summa;
        this.bosh_harf = bosh_harf;
        this.id = id;
        this.sana = sana;
    }

    public String getTanish_ismi() {
        return tanish_ismi;
    }

    public void setTanish_ismi(String tanish_ismi) {
        this.tanish_ismi = tanish_ismi;
    }

    public String getBosh_harf() {
        return bosh_harf;
    }

    public void setBosh_harf(String bosh_harf) {
        this.bosh_harf = bosh_harf;
    }

    public String getSumma() {
        return summa;
    }

    public void setSumma(String summa) {
        this.summa = summa;
    }

    public String getSana() {
        return sana;
    }

    public void setSana(String sana) {
        this.sana = sana;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
