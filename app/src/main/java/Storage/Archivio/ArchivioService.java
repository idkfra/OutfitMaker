package Storage.Archivio;

import com.example.outfitmakerfake.Entity.Capo;
import com.example.outfitmakerfake.Entity.Outfit;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class ArchivioService {
    ArchivioDAO archivioDAO;

    public ArchivioService(ArchivioDAO archivioDAO) {
        this.archivioDAO = archivioDAO;
    }


    public void creaArchivioFirestore(String idArchivio, String uid) {
        archivioDAO.creaArchivioFirestore(idArchivio, uid);
    }

    public String generateUniqueArchivioId() {
        return archivioDAO.generateUniqueArchivioId();
    }
}
