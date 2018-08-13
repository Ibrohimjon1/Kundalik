package com.example.ibrohimjon.kundalik;


public class Kirim_nomi_list {

    String kirim_nomi, bosh_harf;
    int tartib, id;

    public Kirim_nomi_list(String kirim_nomi, int tartib, String bosh_harf, int id) {
        this.kirim_nomi = kirim_nomi;
        this.tartib = tartib;
        this.bosh_harf = bosh_harf;
        this.id = id;
    }

    public String getKirim_nomi() {
        return kirim_nomi;
    }

    public void setKirim_nomi(String kirim_nomi) {
        this.kirim_nomi = kirim_nomi;
    }

    public String getBosh_harf() {
        return bosh_harf;
    }

    public void setBosh_harf(String bosh_harf) {
        this.bosh_harf = bosh_harf;
    }

    public int getTartib() {
        return tartib;
    }

    public void setTartib(int tartib) {
        this.tartib = tartib;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
