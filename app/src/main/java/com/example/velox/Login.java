package com.example.velox;

public class Login {
    private String email;
    private String contraseña;

    public Login() {
    }

    public Login(String email, String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getcontraseña() {
        return contraseña;
    }

    public void setcontraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
