package com.example.outfitmakerfake.GestioneGenerazione;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ControlloPreferenzeMinime {
    String stagionalita;

    @Test
    public void TC1_UC1_RF7_GG1_1_1(){
        stagionalita = "";
        assertTrue("Stagionalità non selezionata", stagionalita.equals(""));
        assertFalse("Stagionalita selezionata", !stagionalita.equals(""));
    }

    @Test
    public void TC1_UC1_RF7_GG1_1_2(){
        stagionalita = "Estivo";
        assertTrue("Stagionalità selezionata", stagionalita.equals("Estivo") || stagionalita.equals("Autunnale") || stagionalita.equals("Primaverile") || stagionalita.equals("Invernale"));
        assertFalse("Stagionalità non selezionata", stagionalita.equals(""));
    }

}
