package com.eddiemartnez.app.mygallery.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.eddiemartnez.app.mygallery.R;
import com.eddiemartnez.app.mygallery.subclases.RunImage;
import com.eddiemartnez.app.mygallery.util.Functions;

/**
 * Created by Brian on 3/11/18.
 */

public class BigImage extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bigimage);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        ImageView imageView = findViewById(R.id.image);
        TextView textView = findViewById(R.id.descripcion);

        RunImage image = new RunImage();
        if(!getIntent().getExtras().isEmpty()) {
            image.name = getIntent().getStringExtra("name");
            image.author = getIntent().getStringExtra("author");
            image.url = getIntent().getStringExtra("url");

            Functions.loadImage(image.url, imageView, this);
            textView.setText("Autor : "+ image.author +"\n" + "Nombre: "+ image.name );
        }else{
            Functions.loadImage( imageView, this);
            textView.setText("No se envio ninguna información" );
        }


    }
}