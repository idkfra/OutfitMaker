package Storage.Utente;

import com.google.android.gms.tasks.Task;

public class RegistrazioneService {

    private UtenteDAO utenteDAO;

    public RegistrazioneService() {
    }

    public RegistrazioneService(UtenteDAO utenteDAO) {
        this.utenteDAO = utenteDAO;
    }

    public Task<Boolean> creaUtente(String nome, String cognome, String email, String password, String telefono) {
        return utenteDAO.creaUtente(nome, cognome, email, password, telefono);
    }
}
