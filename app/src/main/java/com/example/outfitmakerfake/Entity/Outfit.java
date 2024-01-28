package com.example.outfitmakerfake.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Outfit implements Parcelable {
    private String id_outfit;
    private String id_utente;
    private ArrayList<Capo> lista_capi;

    public Outfit() {
    }

    public Outfit(String id_outfit, String id_utente, ArrayList<Capo> lista_capi) {
        this.id_outfit = id_outfit;
        this.id_utente = id_utente;
        this.lista_capi = lista_capi;
    }

    protected Outfit(Parcel in) {
        id_outfit = in.readString();
        id_utente = in.readString();
        lista_capi = in.createTypedArrayList(Capo.CREATOR);
    }

    public static final Creator<Outfit> CREATOR = new Creator<Outfit>() {
        @Override
        public Outfit createFromParcel(Parcel in) {
            return new Outfit(in);
        }

        @Override
        public Outfit[] newArray(int size) {
            return new Outfit[size];
        }
    };

    public String getId_outfit() {
        return id_outfit;
    }

    public void setId_outfit(String id_outfit) {
        this.id_outfit = id_outfit;
    }

    public String getId_utente() {
        return id_utente;
    }

    public void setId_utente(String id_utente) {
        this.id_utente = id_utente;
    }

    public ArrayList<Capo> getLista_capi() {
        return lista_capi;
    }

    public void setLista_capi(ArrayList<Capo> lista_capi) {
        this.lista_capi = lista_capi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_outfit);
        dest.writeString(id_utente);
        dest.writeTypedList(lista_capi);
    }
}