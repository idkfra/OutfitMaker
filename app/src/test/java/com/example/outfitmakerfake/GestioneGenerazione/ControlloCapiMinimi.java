package com.example.outfitmakerfake.GestioneGenerazione;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.example.outfitmakerfake.Entity.Armadio;
import com.example.outfitmakerfake.Entity.Capo;
import com.google.android.gms.tasks.Task;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Storage.Armadio.ArmadioDAO;
import Storage.Armadio.ArmadioService;

public class ControlloCapiMinimi {
    String uid = "987poi654uyt";
    String idArmadio = "123qwe456rty";
    List<Capo> listaCapi = new ArrayList<>();

    @Test
    public void TC1_UC1_RF7_GG1_1(){
        listaCapi = Arrays.asList(
                new Capo("1", "Brand1", Arrays.asList("Rosso", "Blu"), "Pantalone", "Invernale", "Casual"),
                new Capo("2", "Brand2", Arrays.asList("Nero", "Bianco"), "Pantalone", "Estativo", "Formale"),
                new Capo("3", "Brand3", Arrays.asList("Verde", "Giallo"), "Pantalone", "Autunnale", "Elegante"),
                new Capo("4", "Brand4", Arrays.asList("Rosa", "Viola"), "Scarpe", "Primaverile", "Sportivo"),
                new Capo("5", "Brand5", Arrays.asList("Arancione", "Marrone"), "Scarpe", "Invernale", "Casual")
        );

        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        for (Capo capo : listaCapi){
            if(capo.getTipologia().equals("T-shirt") || capo.getTipologia().equals("Felpa")|| capo.getTipologia().equals("Maglia lunga") || capo.getTipologia().equals("Camicia")){

                Task<Boolean> task = Mockito.mock(Task.class);
                when(task.isSuccessful()).thenReturn(true);
                when(armadioService.capiMinimiTop(uid)).thenReturn(task);

                Task<Boolean> resultTask = armadioService.capiMinimiTop(uid);
                assertFalse(resultTask.isSuccessful());
            }
        }

        Task<Boolean> task = Mockito.mock(Task.class);
        when(task.isSuccessful()).thenReturn(false);
        when(armadioService.capiMinimiTop(uid)).thenReturn(task);

        Task<Boolean> resultTask = armadioService.capiMinimiTop(uid);
        assertFalse(resultTask.isSuccessful());
    }

    @Test
    public void TC1_UC1_RF7_GG1_2(){
        listaCapi = Arrays.asList(
                new Capo("1", "Brand1", Arrays.asList("Rosso", "Blu"), "Felpa", "Invernale", "Casual"),
                new Capo("2", "Brand2", Arrays.asList("Nero", "Bianco"), "Felpa", "Estativo", "Formale"),
                new Capo("3", "Brand3", Arrays.asList("Verde", "Giallo"), "Felpa", "Autunnale", "Elegante"),
                new Capo("4", "Brand4", Arrays.asList("Rosa", "Viola"), "Scarpe", "Primaverile", "Sportivo"),
                new Capo("5", "Brand5", Arrays.asList("Arancione", "Marrone"), "Scarpe", "Invernale", "Casual")
        );

        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        for (Capo capo : listaCapi){
            if(capo.getTipologia().equals("Pantalone") || capo.getTipologia().equals("Jeans")|| capo.getTipologia().equals("Pantalone corto") || capo.getTipologia().equals("Gonna")){

                Task<Boolean> task = Mockito.mock(Task.class);
                when(task.isSuccessful()).thenReturn(true);
                when(armadioService.capiMinimiTop(uid)).thenReturn(task);

                Task<Boolean> resultTask = armadioService.capiMinimiTop(uid);
                assertFalse(resultTask.isSuccessful());
            }
        }

        Task<Boolean> task = Mockito.mock(Task.class);
        when(task.isSuccessful()).thenReturn(false);
        when(armadioService.capiMinimiTop(uid)).thenReturn(task);

        Task<Boolean> resultTask = armadioService.capiMinimiTop(uid);
        assertFalse(resultTask.isSuccessful());
    }

    @Test
    public void TC1_UC1_RF7_GG1_3(){
        listaCapi = Arrays.asList(
                new Capo("1", "Brand1", Arrays.asList("Rosso", "Blu"), "Felpa", "Invernale", "Casual"),
                new Capo("2", "Brand2", Arrays.asList("Nero", "Bianco"), "Felpa", "Estativo", "Formale"),
                new Capo("3", "Brand3", Arrays.asList("Verde", "Giallo"), "Felpa", "Autunnale", "Elegante"),
                new Capo("4", "Brand4", Arrays.asList("Rosa", "Viola"), "Pantalone", "Primaverile", "Sportivo"),
                new Capo("5", "Brand5", Arrays.asList("Arancione", "Marrone"), "Pantalone", "Invernale", "Casual")
        );

        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        for (Capo capo : listaCapi){
            if(capo.getTipologia().equals("Scarpe")){

                Task<Boolean> task = Mockito.mock(Task.class);
                when(task.isSuccessful()).thenReturn(true);
                when(armadioService.capiMinimiTop(uid)).thenReturn(task);

                Task<Boolean> resultTask = armadioService.capiMinimiTop(uid);
                assertFalse(resultTask.isSuccessful());
            }
        }

        Task<Boolean> task = Mockito.mock(Task.class);
        when(task.isSuccessful()).thenReturn(false);
        when(armadioService.capiMinimiTop(uid)).thenReturn(task);

        Task<Boolean> resultTask = armadioService.capiMinimiTop(uid);
        assertFalse(resultTask.isSuccessful());
    }

    @Test
    public void TC1_UC1_RF7_GG1_4(){
        int sum_top = 0, sum_center = 0, sum_bottom = 0;
        listaCapi = Arrays.asList(
                new Capo("1", "Brand1", Arrays.asList("Rosso", "Blu"), "T-shirt", "Invernale", "Casual"),
                new Capo("2", "Brand2", Arrays.asList("Nero", "Bianco"), "Felpa", "Estativo", "Formale"),
                new Capo("3", "Brand3", Arrays.asList("Verde", "Giallo"), "Felpa", "Autunnale", "Elegante"),
                new Capo("4", "Brand4", Arrays.asList("Rosa", "Viola"), "Pantalone", "Primaverile", "Sportivo"),
                new Capo("5", "Brand5", Arrays.asList("Arancione", "Marrone"), "Scarpe", "Invernale", "Casual")
        );

        ArmadioDAO armadioDAO = Mockito.mock(ArmadioDAO.class);
        ArmadioService armadioService = new ArmadioService(armadioDAO);

        for (Capo capo : listaCapi){
            if(capo.getTipologia().equals("T-shirt") || capo.getTipologia().equals("Felpa")|| capo.getTipologia().equals("Maglia lunga") || capo.getTipologia().equals("Camicia"))
                sum_top++;
            else if(capo.getTipologia().equals("Pantalone") || capo.getTipologia().equals("Jeans")|| capo.getTipologia().equals("Pantalone corto") || capo.getTipologia().equals("Gonna"))
                sum_center++;
            else if(capo.getTipologia().equals("Scarpe"))
                sum_bottom++;
        }

        if(sum_top > 0 && sum_center > 0 && sum_bottom > 0){
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(true);
            when(armadioService.capiMinimiTop(uid)).thenReturn(task);
            when(armadioService.capiMinimiCenter(uid)).thenReturn(task);
            when(armadioService.capiMinimiBottom(uid)).thenReturn(task);

            Task<Boolean> resultTask_top = armadioService.capiMinimiTop(uid);
            Task<Boolean> resultTask_center = armadioService.capiMinimiCenter(uid);
            Task<Boolean> resultTask_bottom = armadioService.capiMinimiBottom(uid);

            assertTrue(resultTask_top.isSuccessful() && resultTask_center.isSuccessful() && resultTask_bottom.isSuccessful());
        } else {
            Task<Boolean> task = Mockito.mock(Task.class);
            when(task.isSuccessful()).thenReturn(false);
            when(armadioService.capiMinimiTop(uid)).thenReturn(task);
            when(armadioService.capiMinimiCenter(uid)).thenReturn(task);
            when(armadioService.capiMinimiBottom(uid)).thenReturn(task);

            Task<Boolean> resultTask_top = armadioService.capiMinimiTop(uid);
            Task<Boolean> resultTask_center = armadioService.capiMinimiCenter(uid);
            Task<Boolean> resultTask_bottom = armadioService.capiMinimiBottom(uid);

            assertTrue(resultTask_top.isSuccessful() && resultTask_center.isSuccessful() && resultTask_bottom.isSuccessful());
        }
    }
}
