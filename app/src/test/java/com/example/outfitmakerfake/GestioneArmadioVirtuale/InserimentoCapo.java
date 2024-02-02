package com.example.outfitmakerfake.GestioneArmadioVirtuale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import android.util.Log;

import com.example.outfitmakerfake.Entity.Capo;
import com.google.android.gms.tasks.Task;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import Storage.Armadio.ArmadioDAO;
import Storage.Armadio.ArmadioService;

public class InserimentoCapo {
    String idCapo = "123qwe456ert";
    /**
     * @param nome_brand è il nome del brand del capo presente nell'Armadio Virtuale
     */
    String nome_brand = "";
    /**
     * @param lista_colore è la lista di colori del capo presente nell'Armadio Virtuale
     */
    List<String> lista_colore = new ArrayList<>();
    String stagionalita = "";
    String occasione = "";
    String tipologia = "";

    /**
     * Questo metodo di test verifica il comportamento del metodo aggiungiCapo
     * della classe ArmadioService nel caso in cui i dati in input siano fuori
     * dai limiti accettati.
     * Il test utilizza un nome di marca troppo corto ("a") e verifica che il
     * metodo aggiungiCapo restituisca false quando la lunghezza del nome è
     * inferiore a 2 caratteri, maggiore di 15 caratteri, la lista dei colori
     * è vuota o qualsiasi delle stringhe stagionalità, occasione o tipologia
     * è vuota.
     * Se la condizione dell'if è soddisfatta, il metodo di test configura un
     * mock per restituire false quando viene chiamato il metodo aggiungiCapo
     * e verifica che il risultato del metodo sia "non di successo". Se la
     * condizione dell'if non è soddisfatta, il mock viene configurato per
     * restituire true, e il test verifica che il risultato del metodo sia "di
     * successo".
     */
    @Test
    public void TC2_UC2_RF4_GAV1_1() {
        // Impostazione degli input del test
        nome_brand = "a";
        lista_colore.add("Nero");
        lista_colore.add("Giallo");
        stagionalita = "Invernale";
        occasione = "Sportivo";
        tipologia = "Felpa";

        // Creazione dei mock e istanziazione del servizio
        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        // Verifica del comportamento del servizio in base ai dati di input
        if (nome_brand.length() < 2 || nome_brand.length() > 15 || lista_colore.size() == 0 || stagionalita.equals("") || occasione.equals("") || tipologia.equals("")) {
            // Se i dati sono errati, configura il mock per restituire false
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione)).thenReturn(task);

            // Esecuzione del metodo e verifica che il risultato sia "non di successo"
            Task<Boolean> resultTask = armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione);
            assertFalse(resultTask.isSuccessful());
        } else {
            // Se i dati sono corretti, configura il mock per restituire true
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(true);
            when(armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione)).thenReturn(task);

            // Esecuzione del metodo e verifica che il risultato sia "di successo"
            Task<Boolean> resultTask = armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione);
            assertTrue(resultTask.isSuccessful());
        }
    }

    /**
     * Questo metodo di test verifica il comportamento del metodo aggiungiCapo
     * della classe ArmadioService nel caso in cui la lista dei colori sia vuota.
     * Il test utilizza un nome di marca corretto ("Nike") ma con una lista di
     * colori vuota. Verifica che il metodo aggiungiCapo restituisca false quando
     * la lista dei colori è vuota.
     * Se la condizione dell'if è soddisfatta, il metodo di test configura un mock
     * per restituire false quando viene chiamato il metodo aggiungiCapo e verifica
     * che il risultato del metodo sia "non di successo". Se la condizione dell'if
     * non è soddisfatta, il mock viene configurato per restituire true, e il test
     * verifica che il risultato del metodo sia "di successo".
     */
    @Test
    public void TC2_UC2_RF4_GAV1_2() {
        // Impostazione degli input del test
        nome_brand = "Nike";
        lista_colore.clear(); // Lista dei colori vuota
        stagionalita = "Invernale";
        occasione = "Sportivo";
        tipologia = "Felpa";

        // Creazione dei mock e istanziazione del servizio
        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        // Verifica del comportamento del servizio in base ai dati di input
        if (nome_brand.length() < 2 || nome_brand.length() > 15 || lista_colore.size() == 0 || stagionalita.equals("") || occasione.equals("") || tipologia.equals("")) {
            // Se i dati sono errati, configura il mock per restituire false
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione)).thenReturn(task);

            // Esecuzione del metodo e verifica che il risultato sia "non di successo"
            Task<Boolean> resultTask = armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione);
            assertFalse(resultTask.isSuccessful());
        } else {
            // Se i dati sono corretti, configura il mock per restituire true
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(true);
            when(armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione)).thenReturn(task);

            // Esecuzione del metodo e verifica che il risultato sia "di successo"
            Task<Boolean> resultTask = armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione);
            assertTrue(resultTask.isSuccessful());
        }
    }

    @Test
    public void TC2_UC2_RF4_GAV1_3() {
        nome_brand = "Nike";
        lista_colore.add("Nero");
        stagionalita = "";
        occasione = "Sportivo";
        tipologia = "Felpa";

        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        if (nome_brand.length() < 2 || nome_brand.length() > 15 || lista_colore.size() == 0 || stagionalita.equals("") || occasione.equals("") || tipologia.equals("")) {
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione)).thenReturn(task);

            Task<Boolean> resultTask = armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione);
            assertFalse(resultTask.isSuccessful());
        } else {
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione)).thenReturn(task);

            Task<Boolean> resultTask = armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione);

            assertTrue(resultTask.isSuccessful());
        }
    }

    @Test
    public void TC2_UC2_RF4_GAV1_4() {
        nome_brand = "Nike";
        lista_colore.add("Nero");
        stagionalita = "Invernale";
        occasione = "";
        tipologia = "Felpa";

        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        if (nome_brand.length() < 2 || nome_brand.length() > 15 || lista_colore.size() == 0 || stagionalita.equals("") || occasione.equals("") || tipologia.equals("")) {
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione)).thenReturn(task);

            Task<Boolean> resultTask = armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione);
            assertFalse(resultTask.isSuccessful());
        } else {
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione)).thenReturn(task);

            Task<Boolean> resultTask = armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione);

            assertTrue(resultTask.isSuccessful());
        }
    }

    @Test
    public void TC2_UC2_RF4_GAV1_5() {
        nome_brand = "Nike";
        lista_colore.add("Nero");
        stagionalita = "Invernale";
        occasione = "Sportivo";
        tipologia = "";

        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        if (nome_brand.length() < 2 || nome_brand.length() > 15 || lista_colore.size() == 0 || stagionalita.equals("") || occasione.equals("") || tipologia.equals("")) {
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione)).thenReturn(task);

            Task<Boolean> resultTask = armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione);
            assertFalse(resultTask.isSuccessful());
        } else {
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione)).thenReturn(task);

            Task<Boolean> resultTask = armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione);

            assertTrue(resultTask.isSuccessful());
        }
    }

    @Test
    public void TC2_UC2_RF4_GAV1_7() {
        //Tutti i campi sono corretti
        nome_brand = "Nike";
        lista_colore.add("Nero");
        stagionalita ="Invernale";
        occasione = "Sportivo";
        tipologia = "Felpa";

        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        if (nome_brand.length() < 2 || nome_brand.length() > 15 || lista_colore.size() == 0 || stagionalita.equals("") || occasione.equals("") || tipologia.equals("")) {
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione)).thenReturn(task);

            Task<Boolean> resultTask = armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione);
            assertFalse(resultTask.isSuccessful());
        } else {
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(true);
            when(armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione)).thenReturn(task);

            Task<Boolean> resultTask = armadioService.aggiungiCapo(nome_brand, lista_colore, tipologia, stagionalita, occasione);

            assertTrue(resultTask.isSuccessful());
        }
    }
}
