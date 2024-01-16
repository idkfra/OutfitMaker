package ApplicationLogic;

import com.google.android.gms.tasks.Task;

import Storage.RegistrazioneService;

public class RegistrazioneController {

    private RegistrazioneService registrazioneService;

    public RegistrazioneController() {
    }

    public RegistrazioneController(RegistrazioneService registrazioneService) {
        this.registrazioneService = registrazioneService;
    }

    public Task<Boolean> creaUtente(String nome, String cognome, String email, String password, String telefono) {
        return registrazioneService.creaUtente(nome, cognome, email, password, telefono);
    }
}
