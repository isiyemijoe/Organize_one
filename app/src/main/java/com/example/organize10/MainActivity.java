package com.example.organize10;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.protobuf.Api;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Calendar;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    View sign_in;
    ProgressBar mProgressBar;
    GoogleSignInClient mSignInClient;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();


        mProgressBar = findViewById(R.id.progressBar);
        sign_in = findViewById(R.id.google);
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser() != null){

                    FirebaseUser user = mAuth.getCurrentUser();
                    String email = user.getEmail();
                    String photo = String.valueOf(user.getPhotoUrl());
                    Intent intent = new Intent(MainActivity.this,home.class);

                    intent.putExtra("email", email);
                    intent.putExtra("photo",photo);
                    Log.d("TAG","NAME AND EMAIL NOT NULL");
                    startActivity(intent);
                }
            }
        };
        GoogleSignInOptions options = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString( R.string.default_web_client_id))
                .requestEmail()
                .build();


        mSignInClient = GoogleSignIn.getClient(this,options);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });




    }


    void signInGoogle(){
            mProgressBar.setVisibility(View.VISIBLE);
        Intent signIntent = mSignInClient.getSignInIntent();
        startActivityForResult(signIntent, 123);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 123){
            Task<GoogleSignInAccount> task = GoogleSignIn.
                    getSignedInAccountFromIntent(data);


            try {

                //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
                //updateUI(account);
                GoogleSignInAccount account = task.getResult(ApiException.class);

                    if(account != null){
                        firebaseAuthWithGoogle(account);
                    }
            } catch (ApiException e){
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                mProgressBar.setVisibility(View.INVISIBLE);
                                Log.d("TAG","Signin Success");

                                FirebaseUser user = mAuth.getCurrentUser();
                                 updateUI(user);
                            }else{
                                mProgressBar.setVisibility(View.INVISIBLE);
                                Log.d("TAG","Signin failure");
                                makeText(getApplicationContext(),"Signin failed", LENGTH_SHORT).show();
                                updateUI(null);
                            }
                    }
                });
    }


    private  void updateUI(FirebaseUser user){
        if(user != null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl());
           Intent intent = new Intent(this,welcome_screen.class);


                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    intent.putExtra("photo",photo);
                    Log.d("TAG","NAME AND EMAIL NOT NULL");
                    startActivity(intent);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
