package com.proyecto.centraldetaxi.activities.client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.proyecto.centraldetaxi.R;
import com.proyecto.centraldetaxi.includes.MyToolbar;
import com.proyecto.centraldetaxi.models.Client;
import com.proyecto.centraldetaxi.providers.AuthProvider;
import com.proyecto.centraldetaxi.providers.ClientProvider;

public class RegisterActivity2 extends AppCompatActivity {

    SharedPreferences mPref;


    AuthProvider mAuthProvider;
    ClientProvider mClientProvider;

    //VIEWS
    Button mButtonRegister;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;

    private ProgressBar mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        MyToolbar.show(this,"Registro de usuario",true);

        mAuthProvider = new AuthProvider();
        mClientProvider = new ClientProvider();

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);


        mDialog = findViewById(R.id.progressBar3);
        //String selectedUser = mPref.getString("user", "");
        //Toast.makeText(this, "Seleccionó " + selectedUser, Toast.LENGTH_SHORT).show();
        mButtonRegister = findViewById(R.id.botonRegister);

        mTextInputName = findViewById(R.id.textInputName);
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }

    void registerUser(){
        String name = mTextInputName.getText().toString();
        String email = mTextInputEmail.getText().toString();
        String password = mTextInputPassword.getText().toString();

        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if(password.length() >= 6){

                mDialog.setVisibility(View.VISIBLE);

                register(name, email, password);


            }else{
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }


    }
    void register(String name, String email, String password){
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                mDialog.setVisibility(View.GONE);

                if(task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    Client client = new Client(id, name, email);

                    create(client);

                }else{
                    Toast.makeText(RegisterActivity2.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void create(Client client){
        mClientProvider.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(RegisterActivity2.this, "El registro se realizó exitosamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity2.this, MapClientActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{
                    Toast.makeText(RegisterActivity2.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    /*void saveUser(String id, String name, String email){
        String selectedUser = mPref.getString("user", "");

        User user = new User();

        user.setEmail(email);
        user.setName(name);

        if(selectedUser.equals("conductor")){
            mDatabase.child("Users").child("Conductores").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity2.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(RegisterActivity2.this, "Falló el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else if(selectedUser.equals("client")){
            mDatabase.child("Users").child("Clients").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity2.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(RegisterActivity2.this, "Falló el registro", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


    }*/
}