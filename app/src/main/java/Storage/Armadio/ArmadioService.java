package Storage.Armadio;

import com.example.outfitmakerfake.Entity.Capo;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Storage.Armadio.ArmadioDAO;

public class ArmadioService {

    ArmadioDAO armadioDAO;

    public ArmadioService() {
    }

    public ArmadioService(ArmadioDAO armadioDAO) {
        this.armadioDAO = armadioDAO;
    }

    public Task<Boolean> aggiungiCapo(String nomeBrand, List<String> colori, String tipologia, String stagionalità, String occasione){
        return armadioDAO.aggiungiCapo(nomeBrand, colori, tipologia, stagionalità, occasione);
    }

    public Task<Boolean> modificaCapo(String nomeBrand, List<String> colori, String tipologia, String stagionalità, String occasione){
        return armadioDAO.modificaCapo(nomeBrand, colori, tipologia, stagionalità, occasione);
    }

    public Task<ArrayList<Capo>> ricercaFiltri(ArrayList<String> colori, String stagionalita, String tipologia) {
        return armadioDAO.ricercaFiltri(colori, stagionalita, tipologia);
    }

    public Task<Boolean> resettaSceltaCapi(){
        return armadioDAO.resettaSceltaCapi();
    }

    public Task<Boolean> creaOutfit(ArrayList<Capo> lista_capi){
        return armadioDAO.creaOutfit(lista_capi);
    }

    public Task<ArrayList<Capo>> generaOutfit(String stagionalita) throws ExecutionException, InterruptedException {
        return armadioDAO.generaOutfit(stagionalita);
    }


    public Task<Boolean> capiMinimiTop(String uid){
        return armadioDAO.capiMinimiTop(uid);
    }

    public Task<Boolean> capiMinimiCenter(String uid){
        return armadioDAO.capiMinimiCenter(uid);
    }

    public Task<Boolean> capiMinimiBottom(String uid){
        return armadioDAO.capiMinimiBottom(uid);
    }

}
