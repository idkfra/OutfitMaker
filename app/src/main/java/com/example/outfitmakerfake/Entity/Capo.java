package com.example.outfitmakerfake.Entity;

import android.graphics.drawable.Drawable;

public class Capo {
    private String id_indumento;
    private String nome_brand;
    private String colore;
    private String tipologia;
    private String stagionalita;
    private Drawable immagine;


    public Capo() {
    }

    public Capo(String id_indumento, String nome_brand, String colore, String tipologia, String stagionalita){
        this.id_indumento=id_indumento;
        this.nome_brand=nome_brand;
        this.colore=colore;
        this.tipologia=tipologia;
        this.stagionalita=stagionalita;
    }

    public Capo(String id_indumento, String nome_brand, String colore, String tipologia, String stagionalita, Drawable immagine) {
        this.id_indumento = id_indumento;
        this.nome_brand = nome_brand;
        this.colore = colore;
        this.tipologia = tipologia;
        this.stagionalita = stagionalita;
        this.immagine = immagine;
    }

    public String getId_indumento() {
        return id_indumento;
    }

    public void setId_indumento(String id_indumento) {
        this.id_indumento = id_indumento;
    }

    public String getnome_brand() {
        return nome_brand;
    }

    public void setnome_brand(String nome_brand) {
        this.nome_brand = nome_brand;
    }

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getStagionalita() {
        return stagionalita;
    }

    public void setStagionalita(String stagionalita) {
        this.stagionalita = stagionalita;
    }

    public Drawable getImmagine() {
        return immagine;
    }

    public void setImmagine(Drawable immagine) {
        this.immagine = immagine;
    }
}