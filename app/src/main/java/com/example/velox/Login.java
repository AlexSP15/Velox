package com.example.velox;

public class Login {
    private String email;
    private String contraseña;
    private String tipousuario;

    public Login() {
    }

    public Login(String email, String contraseña, String tipousuario) {
        this.email = email;
        this.contraseña = contraseña;
        this.tipousuario = tipousuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(String tipousuario) {
        this.tipousuario = tipousuario;
    }
}
