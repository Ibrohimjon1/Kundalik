package com.example.ibrohimjon.kundalik;

public class Savdo_list_yangi {

    String  summa;
    String dokNom, shotNom; int id;
    int dok_visibility;

    public Savdo_list_yangi(String dokNom, String shotNom, String summa, int id) {
        this.dokNom = dokNom;
        this.shotNom = shotNom;
        this.summa = summa;
        this.id = id;
    }

    public Savdo_list_yangi(String dokNom, String shotNom, String summa, int id, int dok_visibility) {
        this.dokNom = dokNom;
        this.shotNom = shotNom;
        this.summa = summa;
        this.id = id;
        this.dok_visibility = dok_visibility;
    }

    public String getDokNom() {
        return dokNom;
    }

    public void setDokNom(String dokNom) {
        this.dokNom = dokNom;
    }

    public String getShotNom() {
        return shotNom;
    }

    public void setShotNom(String shotNom) {
        this.shotNom = shotNom;
    }

    public String getSumma() {
        return summa;
    }

    public void setSumma(String summa) {
        this.summa = summa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDok_visibility() {
        return dok_visibility;
    }

    public void setDok_visibility(int dok_visibility) {
        this.dok_visibility = dok_visibility;
    }
}
