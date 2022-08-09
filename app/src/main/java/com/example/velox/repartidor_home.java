package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class repartidor_home extends AppCompatActivity {
    private ImageView btnEntregaR, btnNuevaR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repartidor_home);
        bindUI();
        btnEntregaR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMain();
            }
        });
        btnNuevaR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               gotoEntrega();
            }
        });
    }
    private void bindUI(){
        btnEntregaR = (ImageView) findViewById(R.id.btnRepEntrega);
        btnNuevaR = (ImageView) findViewById(R.id.btnNewEnvioRep);
    }
    private void gotoMain(){
        Intent intent = new Intent(repartidor_home.this,Repartidor_Rastreo.class);
        startActivity(intent);
    }

    private void gotoEntrega(){
        Intent intent = new Intent(repartidor_home.this,Repartidor_NuevaEntrega.class);
        startActivity(intent);
    }
}