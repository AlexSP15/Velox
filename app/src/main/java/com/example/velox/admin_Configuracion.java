package com.example.velox;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class admin_Configuracion extends AppCompatActivity {
    private ImageView usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_configuracion);
        bindUI();
        usuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMain();
            }
        });
    }
    private void bindUI(){
        usuarios = (ImageView) findViewById(R.id.btnUsuarios);
    }
    private void gotoMain(){
        Intent intent = new Intent (admin_Configuracion.this, usuarios_admin.class);
        startActivity(intent);
    }
}