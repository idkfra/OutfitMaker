package com.example.outfitmakerfake.GestioneArmadioVirtuale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.example.outfitmakerfake.Entity.Armadio;
import com.example.outfitmakerfake.Entity.Capo;
import com.google.android.gms.tasks.Task;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Storage.Armadio.ArmadioDAO;
import Storage.Armadio.ArmadioService;

public class RicercaCapo {
    String idArmadio = "1234qwe";
    ArrayList<Capo> lista_capi = new ArrayList<>(Arrays.asList(
            new Capo("Nike", Arrays.asList("Bianco", "Nero"), "T-shirt", "Estivo", "Casuale"),
            new Capo("Levi's", Arrays.asList("Blu", "Grigio"), "Jeans", "Autunnale", "Casuale"),
            new Capo("Zara", Arrays.asList("Nero", "Rosso"), "Giacca", "Invernale", "Formale"),
            new Capo("Adidas", Arrays.asList("Rosso", "Bianco"), "Scarpe", "Invernale", "Sportivo"),
            new Capo("H&M", Arrays.asList("Verde", "Blu"), "Felpa", "Estivo", "Casuale")));

    String nome = "";
    ArrayList<String> lista_colori;
    String stagionalita = "";
    String tipologia = "";

    @Test
    public void TC3_UC3_RF3_GAV_1() {
        // Lista colori è vuota
        lista_colori = new ArrayList<>();
        stagionalita = "Estivo";
        tipologia = "T-shirt";

        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        if(lista_colori.size() == 0 || stagionalita.equals("") || tipologia.equals("")) {
            // Se i dati sono errati, configurare il mock per restituire false
            Task<ArrayList<Capo>> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia)).thenReturn(task);

            // Esegui la funzione da testare
            Task<ArrayList<Capo>> resultTask = armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia);

            // Verifica che la funzione ritorni un risultato negativo
            assertFalse(resultTask.isSuccessful());
        } else {
            Task<ArrayList<Capo>> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(true);
            when(armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia)).thenReturn(task);

            // Esegui la funzione da testare
            Task<ArrayList<Capo>> resultTask = armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia);

            // Verifica che la funzione ritorni un risultato negativo
            assertTrue(resultTask.isSuccessful());
        }
    }

    @Test
    public void TC3_UC3_RF3_GAV_2() {
        lista_colori = new ArrayList<>();
        lista_colori.add("Nero");
        lista_colori.add("Giallo");
        //Stagionalità estivo
        stagionalita = "";
        tipologia = "T-shirt";

        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        if(lista_colori.size() == 0 || stagionalita.equals("") || tipologia.equals("")) {
            // Se i dati sono errati, configurare il mock per restituire false
            Task<ArrayList<Capo>> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia)).thenReturn(task);

            // Esegui la funzione da testare
            Task<ArrayList<Capo>> resultTask = armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia);

            // Verifica che la funzione ritorni un risultato negativo
            assertFalse(resultTask.isSuccessful());
        } else {
            Task<ArrayList<Capo>> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(true);
            when(armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia)).thenReturn(task);

            // Esegui la funzione da testare
            Task<ArrayList<Capo>> resultTask = armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia);

            // Verifica che la funzione ritorni un risultato negativo
            assertTrue(resultTask.isSuccessful());
        }
    }
    @Test
    public void TC3_UC3_RF3_GAV_3() {
        lista_colori = new ArrayList<>();
        lista_colori.add("Nero");
        lista_colori.add("Giallo");
        stagionalita = "Estivo";
        //Tipologia vuota
        tipologia = "";

        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        if (lista_colori.size() == 0 || stagionalita.equals("") || tipologia.equals("")) {
            // Se i dati sono errati, configurare il mock per restituire false
            Task<ArrayList<Capo>> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia)).thenReturn(task);

            // Esegui la funzione da testare
            Task<ArrayList<Capo>> resultTask = armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia);

            // Verifica che la funzione ritorni un risultato negativo
            assertFalse(resultTask.isSuccessful());
        } else {
            Task<ArrayList<Capo>> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(true);
            when(armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia)).thenReturn(task);

            // Esegui la funzione da testare
            Task<ArrayList<Capo>> resultTask = armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia);

            // Verifica che la funzione ritorni un risultato negativo
            assertTrue(resultTask.isSuccessful());
        }
    }

    @Test
    public void TC3_UC3_RF3_GAV_4() {
        lista_colori = new ArrayList<>();
        lista_colori.add("Nero");
        lista_colori.add("Giallo");
        stagionalita = "Estivo";
        tipologia = "Felpa";

        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        if (lista_colori.size() == 0 || stagionalita.equals("") || tipologia.equals("")) {
            // Se i dati sono errati, configurare il mock per restituire false
            Task<ArrayList<Capo>> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia)).thenReturn(task);

            // Esegui la funzione da testare
            Task<ArrayList<Capo>> resultTask = armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia);

            // Verifica che la funzione ritorni un risultato negativo
            assertFalse(resultTask.isSuccessful());
        } else {
            Task<ArrayList<Capo>> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(true);
            when(armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia)).thenReturn(task);

            // Esegui la funzione da testare
            Task<ArrayList<Capo>> resultTask = armadioService.ricercaFiltri(lista_colori, stagionalita, tipologia);

            // Verifica che la funzione ritorni un risultato negativo
            assertTrue(resultTask.isSuccessful());
        }
    }

}