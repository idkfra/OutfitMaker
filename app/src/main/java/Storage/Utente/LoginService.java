package Storage.Utente;

import com.google.android.gms.tasks.Task;

public class LoginService {

    private UtenteDAO utenteDAO;

    public LoginService() {
    }

    public LoginService(UtenteDAO utenteDAO) {
        this.utenteDAO = utenteDAO;
    }

    public Task<Boolean> effettuaLogin(String email, String password){
        return utenteDAO.effettuaLogin(email, password);
    }
}
