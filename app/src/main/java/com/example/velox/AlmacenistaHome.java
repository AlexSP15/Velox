package com.example.velox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AlmacenistaHome extends AppCompatActivity {
    private ImageView ingresar;
    private ImageView despachar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacenista_home);
        bindUI();
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMain();
            }
        });

        despachar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoDes();
            }
        });
    }

    private void bindUI() {
        ingresar = (ImageView) findViewById(R.id.btnAlmacenar);
        despachar = (ImageView) findViewById(R.id.btnDespachar);
    }

    private void gotoMain() {
        Intent intent = new Intent(AlmacenistaHome.this, Almacenar.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void gotoDes() {
        Intent intent = new Intent(AlmacenistaHome.this, despachaEnvio.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}