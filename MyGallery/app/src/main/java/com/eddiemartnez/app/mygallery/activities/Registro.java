package com.eddiemartnez.app.mygallery.activities;

/**
 * Created by Eddie Martínez on 9/4/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.eddiemartnez.app.mygallery.R;
import com.eddiemartnez.app.mygallery.db.Models.Users;
import com.eddiemartnez.app.mygallery.util.Functions;
import com.eddiemartnez.app.mygallery.util.Session;

/**
 * Created by Brian on 3/26/18.
 */

public class Registro extends AppCompatActivity{

    private EditText username;
    private EditText password;

    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerview);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


        Button iniciar = findViewById(R.id.register);
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario(){
        if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            Toast.makeText(this, R.string.register_error, Toast.LENGTH_LONG).show();
        }else{
            Users user = new Users();
            user.password = Functions.md5(password.getText().toString());
            user.username = username.getText().toString();
            user.save();
            Session session = new Session(this);
            session.createLoginSession(user.id,user.nombre, user.roll);
            goToMain();
            finish();
        }
    }

    private  void goToMain(){
        Intent i = new Intent(getApplicationContext(), Main.class);
        startActivity(i);
        finish();
    }
}