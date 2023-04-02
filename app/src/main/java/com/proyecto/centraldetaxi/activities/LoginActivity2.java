package com.proyecto.centraldetaxi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.centraldetaxi.R;
import com.proyecto.centraldetaxi.activities.client.MapClientActivity;
import com.proyecto.centraldetaxi.activities.client.RegisterActivity2;
import com.proyecto.centraldetaxi.activities.driver.MapDriverActivity;
import com.proyecto.centraldetaxi.activities.driver.RegisterDriverActivity;
import com.proyecto.centraldetaxi.includes.MyToolbar;

public class LoginActivity2 extends AppCompatActivity {

    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonLogin;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    SharedPreferences mPref;

    TextView mTextView;

    private ProgressBar mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        MyToolbar.show(this,"Login de usuario",true);


        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mButtonLogin = findViewById(R.id.botonLogin);
        mTextView = findViewById(R.id.newUser);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        mDialog = findViewById(R.id.progressBar3);


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });



    }

    private void login(){

        String email = mTextInputEmail.getText().toString();
        String password = mTextInputPassword.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()){
            if(password.length() >= 6){

                mDialog.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String user = mPref.getString("user", "");
                            if(user.equals("client")){
                                Intent intent = new Intent(LoginActivity2.this, MapClientActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }else{
                                Intent intent = new Intent(LoginActivity2.this, MapDriverActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                            Toast.makeText(LoginActivity2.this, "Sesión iniciada", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(LoginActivity2.this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }


                        mTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                goToRegister();
                            }
                        });


                        mDialog.setVisibility(View.GONE);


                    }
                });

            }else{
                Toast.makeText(this, "La contraseña debe tener más de 6 caracteres", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "No dudes en registrarte", Toast.LENGTH_SHORT).show();

        }

    }

    public void goToRegister(){

        String typeUser = mPref.getString("user", "");

        Intent intent;
        if(typeUser.equals("client")){

            intent = new Intent(LoginActivity2.this, RegisterActivity2.class);
        }else{
            intent = new Intent(LoginActivity2.this, RegisterDriverActivity.class);

        }
        startActivity(intent);

    }
}
