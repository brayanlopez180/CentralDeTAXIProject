package com.proyecto.centraldetaxi.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.proyecto.centraldetaxi.R;

public class WelcomeMyAppActivity2 extends AppCompatActivity {

    Button mButtonContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_my_app2);

        mButtonContinuar = findViewById(R.id.botonContinuar);

        mButtonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InicioAppActivity2();
            }
        });

    }


}