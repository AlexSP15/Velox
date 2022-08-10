package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class inicioAdmin extends AppCompatActivity {
        private ImageView btnConf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_admin);
        bindUI();

        btnConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMain();
            }
        });
    }
    private void bindUI(){
        btnConf = (ImageView) findViewById(R.id.btnConfiguracion);
    }
    private void gotoMain(){
        Intent intent = new Intent(inicioAdmin.this, admin_Configuracion.class);
        startActivity(intent);
    }
}