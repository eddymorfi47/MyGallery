package com.eddiemartnez.app.mygallery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eddiemartnez.app.mygallery.R;
import com.eddiemartnez.app.mygallery.db.Models.Users;
import com.eddiemartnez.app.mygallery.db.Models.Users_Table;
import com.eddiemartnez.app.mygallery.util.Functions;
import com.eddiemartnez.app.mygallery.util.Session;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by Brian on 3/26/18.
 */

public class intro extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Session session;
    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


        session = new Session(this);
        if(session.isLoggedIn()){
            goToMain();
        }
        Button iniciar = findViewById(R.id.login);
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userna = username.getText().toString();
                String passwo = password.getText().toString();
                if (userna.isEmpty() || passwo.isEmpty()) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginerror), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Login(userna, passwo);
                }
            }
        });


        Button registrar = findViewById(R.id.register);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegistrar();
            }
        });


        Functions.loadImage("http://reconciliasian.com/wp-content/uploads/2018/03/gallery-perfect-7-piece-wide-frame-set-modern-picture-frames-with-prepare.jpg", image, this);
    }

    private  void goToMain(){
        Intent i = new Intent(getApplicationContext(), Main.class);
        startActivity(i);
        finish();
    }

    private  void goToRegistrar(){
        Intent i = new Intent(getApplicationContext(), Registro.class);
        startActivity(i);
        finish();
    }

    private boolean Login(String username, String password){
        boolean isLoggedIn= false;
        isLoggedIn = isLoggedIn = SQLite.selectCountOf().from(Users.class).where(Users_Table.username.eq(username)).and(Users_Table.password.eq(Functions.md5(password))).hasData();
        Users user = SQLite.select().from(Users.class).where(Users_Table.username.eq(username)).and(Users_Table.password.eq(Functions.md5(password))).querySingle();

        if (isLoggedIn){
            session.createLoginSession(user.id,user.nombre, user.roll);
            goToMain();
        }else{
            Toast.makeText(this, getResources().getString(R.string.tryregister), Toast.LENGTH_LONG).show();
        }
        return isLoggedIn;
    }

}
}
