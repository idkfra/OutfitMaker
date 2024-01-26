package com.example.outfitmakerfake.Entity;

import java.util.ArrayList;

public class Outfit {
    private String id_outfit;
    private String id_utente;
    private ArrayList<Capo> lista_outfit;

    public Outfit(String id_outfit, String id_utente, ArrayList<Capo> lista_outfit) {
        this.id_outfit = id_outfit;
        this.id_utente = id_utente;
        this.lista_outfit = lista_outfit;
    }

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

    public ArrayList<Capo> getLista_outfit() {
        return lista_outfit;
    }

    public void setLista_outfit(ArrayList<Capo> lista_outfit) {
        this.lista_outfit = lista_outfit;
    }
}
