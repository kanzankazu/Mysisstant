package id.co.halloarif.catatanku.presenter;

public interface LogRegListener {
    void onRegister(/*String phoneEmail, String password*/);

    void onLogin(/*String phoneEmail, String password*/);

    void onActivation(/*String phoneEmail, String password*/);

    void onForgetPass(/*String phoneEmail, String password*/);
}
