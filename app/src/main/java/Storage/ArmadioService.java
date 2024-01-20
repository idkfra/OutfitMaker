package Storage;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.android.gms.tasks.Task;

import java.util.List;

public class ArmadioService {

    ArmadioDAO armadioDAO;

    public ArmadioService() {
    }

    public ArmadioService(ArmadioDAO armadioDAO) {
        this.armadioDAO = armadioDAO;
    }

    public Task<Boolean> aggiungiCapo(String id, String nomeBrand, List<String> colori, String tipologia, String stagionalità, String occasione){
        return armadioDAO.aggiungiCapo(id, nomeBrand, colori, tipologia, stagionalità, occasione);
    }

    public String generateIndumentoID(){
        return armadioDAO.generateUniqueIndumentoId();
    }

    /*public Task<String> getIdArmadio(){
        return armadioDAO.getIdArmadioUtenteCorrente();
    }*/

}
