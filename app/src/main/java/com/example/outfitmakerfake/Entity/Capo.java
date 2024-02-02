package com.example.outfitmakerfake.Entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Capo implements Parcelable {
    private String id_indumento;
    private String id_armadio;
    private String nome_brand;
    private List<String> colori;
    private String tipologia;
    private String stagionalita;
    private String occasione;
    private String imageUri;
    private Boolean isScelto = true;

    public Capo() {
    }

    public Capo(String id_indumento, String id_armadio, String nome_brand, List<String> colori, String tipologia, String stagionalita, String occasione, String imageUri) {
        this.id_indumento = id_indumento;
        this.id_armadio = id_armadio;
        this.nome_brand = nome_brand;
        this.colori = colori;
        this.tipologia = tipologia;
        this.stagionalita = stagionalita;
        this.occasione = occasione;
        this.imageUri = imageUri;
    }

    public Capo(String nome_brand, List<String> colori, String tipologia, String stagionalita, String occasione, Boolean isScelto) {
        this.nome_brand = nome_brand;
        this.colori = colori;
        this.tipologia = tipologia;
        this.stagionalita = stagionalita;
        this.occasione = occasione;
        this.isScelto = isScelto;
    }

    public Capo(String id_indumento, String id_armadio, String nome_brand, List<String> colori, String tipologia, String stagionalita, String occasione) {
        this.id_indumento = id_indumento;
        this.id_armadio = id_armadio;
        this.nome_brand = nome_brand;
        this.colori = colori;
        this.tipologia = tipologia;
        this.stagionalita = stagionalita;
        this.occasione = occasione;
    }

    public Capo(String nome_brand, List<String> colori, String tipologia, String stagionalita, String occasione) {
        this.nome_brand = nome_brand;
        this.colori = colori;
        this.tipologia = tipologia;
        this.stagionalita = stagionalita;
        this.occasione = occasione;
    }

    public Capo(String id_indumento, String nome_brand, List<String> colori, String tipologia, String stagionalita, String occasione) {
        this.id_indumento = id_indumento;
        this.nome_brand = nome_brand;
        this.colori = colori;
        this.tipologia = tipologia;
        this.stagionalita = stagionalita;
        this.occasione = occasione;
    }

    /*public Capo(String id_indumento, String nome_brand, List<String> colori, String tipologia, String stagionalita, String occasione, Bitmap immagine) {
        this.id_indumento = id_indumento;
        this.nome_brand = nome_brand;
        this.colori = colori;
        this.tipologia = tipologia;
        this.stagionalita = stagionalita;
        this.occasione = occasione;
        this.immagine = immagine;
    }*/

    protected Capo(Parcel in) {
        id_indumento = in.readString();
        id_armadio = in.readString();
        nome_brand = in.readString();
        colori = in.createStringArrayList();
        tipologia = in.readString();
        stagionalita = in.readString();
        occasione = in.readString();
        imageUri = in.readString();
        byte tmpIsScelto = in.readByte();
        isScelto = tmpIsScelto == 1;
    }

    public static final Creator<Capo> CREATOR = new Creator<Capo>() {
        @Override
        public Capo createFromParcel(Parcel in) {
            return new Capo(in);
        }

        @Override
        public Capo[] newArray(int size) {
            return new Capo[size];
        }
    };

    public String getId_indumento() {
        return id_indumento;
    }

    public void setId_indumento(String id_indumento) {
        this.id_indumento = id_indumento;
    }

    public String getNome_brand() {
        return nome_brand;
    }

    public void setNome_brand(String nome_brand) {
        this.nome_brand = nome_brand;
    }

    public List<String> getColori() {
        return colori;
    }

    public void setColori(List<String> colori) {
        this.colori = colori;
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

    public String getOccasione() {
        return occasione;
    }

    public void setOccasione(String occasione) {
        this.occasione = occasione;
    }

    public String getId_armadio() {
        return id_armadio;
    }

    public void setId_armadio(String id_armadio) {
        this.id_armadio = id_armadio;
    }

    public Boolean getScelto() {
        return isScelto;
    }

    public void setScelto(Boolean scelto) {
        isScelto = scelto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_indumento);
        dest.writeString(id_armadio);
        dest.writeString(nome_brand);
        dest.writeStringList(colori);
        dest.writeString(tipologia);
        dest.writeString(stagionalita);
        dest.writeString(occasione);
        dest.writeString(imageUri);
        dest.writeByte((byte) (isScelto ? 1 : 0));
    }
}