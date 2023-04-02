package com.proyecto.centraldetaxi.activities.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.proyecto.centraldetaxi.R;
import com.proyecto.centraldetaxi.includes.MyToolbar;
import com.proyecto.centraldetaxi.models.Driver;
import com.proyecto.centraldetaxi.providers.AuthProvider;
import com.proyecto.centraldetaxi.providers.DriverProvider;

public class RegisterDriverActivity extends AppCompatActivity {

    SharedPreferences mPref;

    AuthProvider mAuthProvider;
    DriverProvider mDriverProvider;
    //VIEWS
    Button mButtonRegister;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputName;

    TextInputEditText mTextInputVehicleBrand;
    TextInputEditText mTextInputVehiclePlate;
    TextInputEditText mTextInputPassword;

    private ProgressBar mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        MyToolbar.show(this, "Registro de conductor", true);

        mAuthProvider = new AuthProvider();
        mDriverProvider = new DriverProvider();

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        mDialog = findViewById(R.id.progressBar3);

        mButtonRegister = findViewById(R.id.botonRegister);

        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputName = findViewById(R.id.textInputName);
        mTextInputVehicleBrand = findViewById(R.id.textInputVehicleBrand);
        mTextInputVehiclePlate = findViewById(R.id.textInputVehiclePlate);
        mTextInputPassword = findViewById(R.id.textInputPassword);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }

    void registerUser() {

        String name = mTextInputName.getText().toString();
        String email = mTextInputEmail.getText().toString();
        String vehicleBrand = mTextInputVehicleBrand.getText().toString();
        String vehiclePlate = mTextInputVehiclePlate.getText().toString();
        String password = mTextInputPassword.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !vehicleBrand.isEmpty() && !vehiclePlate.isEmpty()) {
            if (password.length() >= 6) {
                register(name, email, password, vehicleBrand, vehiclePlate);

                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
            }

        }

    }

    void register(String name, String email, String password, String vehicleBrand, String vehiclePlate) {
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                mDialog.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    Driver driver = new Driver(id, name, email, vehicleBrand, vehiclePlate);

                    create(driver);

                } else {
                    Toast.makeText(RegisterDriverActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create(Driver driver) {
        mDriverProvider.create(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(RegisterDriverActivity.this, "El registro se realizó exitosamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterDriverActivity.this, MapDriverActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(RegisterDriverActivity.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}