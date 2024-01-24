package Storage;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.example.outfitmakerfake.Entity.Capo;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

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

}
