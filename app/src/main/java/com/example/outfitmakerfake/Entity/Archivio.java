package com.example.outfitmakerfake.Entity;

import java.util.ArrayList;
import java.util.Arrays;

public class Archivio {
    private String id_archivio;
    private ArrayList<Outfit> outfits;
    private String uid;

    public Archivio(String id_archivio, ArrayList<Outfit> outfits, String uid) {
        this.id_archivio = id_archivio;
        this.outfits = outfits;
        this.uid = uid;
    }

    public String getId_archivio() {
        return id_archivio;
    }

    public void setId_archivio(String id_archivio) {
        this.id_archivio = id_archivio;
    }

    public ArrayList<Outfit> getOutfits() {
        return outfits;
    }

    public void setOutfits(ArrayList<Outfit> outfits) {
        this.outfits = outfits;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
