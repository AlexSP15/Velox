package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RecepcionH extends AppCompatActivity {
    private ImageView RegistrarNE;
    private ImageView RastrearE;
    private  ImageView EntregaE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepcion_h);
        bindUI();
        RegistrarNE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRNE();
            }
        });

        RastrearE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRAE();
            }
        });

        EntregaE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoEE();
            }
        });
    }
    private void bindUI() {
       RegistrarNE = (ImageView) findViewById(R.id.btnRegistrarNuevoEnvio);
       RastrearE = (ImageView) findViewById(R.id.RastrearEnvio);
       EntregaE = (ImageView) findViewById(R.id.btnEntregarEnvio);

    }
    private void gotoRNE() {
        Intent intent = new Intent(RecepcionH.this, RecepcionNuevaEntrega.class);
        startActivity(intent);
    }
    private void gotoRAE() {
        Intent intent = new Intent(RecepcionH.this, RecepcionRastreo.class);
        startActivity(intent);
    }
    private void gotoEE() {
        Intent intent = new Intent(RecepcionH.this, RecepcionEntrega.class);
        startActivity(intent);
    }
}