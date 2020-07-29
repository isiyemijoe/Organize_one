package com.example.organize10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.GlideModule;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class welcome_screen extends AppCompatActivity {

    CircleImageView pPhoto;
    TextView pname;
    TextView getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        pPhoto = findViewById(R.id.profile_photo);
        pname = findViewById(R.id.welcome_text);
        getStarted = findViewById(R.id.getStarted);


        Bundle bundle = getIntent().getExtras();
        String photo = bundle.getString("photo");
        String email = bundle.getString("email");

        Glide.with(getApplicationContext())
                .load(photo)
                .into(pPhoto);

        getStarted.setOnClickListener(v -> {
            Intent intent = new Intent(welcome_screen.this,home.class);
            startActivity(intent);
        });


    }

}
