package ApplicationLogic;

import com.google.android.gms.tasks.Task;

import Storage.LoginService;

public class LoginController {
    private LoginService loginService;

    public LoginController() {
    }

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    public Task<Boolean> effettuaLogin(String email, String password){
        return loginService.effettuaLogin(email, password);
    }
}
