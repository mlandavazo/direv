package com.direv.direv;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /* 
        firebaseAuth.fetchProvidersForEmail();
        firebaseAuth.getCurrentUser();
        firebaseAuth.sendPasswordResetEmail();
        firebaseAuth.signInAnonymously();
        firebaseAuth.signInWithEmailAndPassword();
        firebaseAuth.signOut();
        firebaseAuth.signInWithCustomToken();
    */




    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
/*
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
*/
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MainTabsAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        int[] imageResId = {
                R.drawable.homeicon,
                R.drawable.feedicon,
                R.drawable.cameraicon,
                R.drawable.usernameicon,
                R.drawable.settingsicon};

        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }
    }
/*
    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            // Email is empty
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            // Stopping the function from proceeding
            return;
        }

        if(TextUtils.isEmpty(password)) {
            // Password is empty
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            // Stopping the function from proceeding
            return;
        }

        // If validation is ok
        // First show progress bar

        progressDialog.setMessage("Registering User...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        // User is successfully registered and logged in
                        // We will start the profile activity here
                        // Right now lets display a toast only
                        Toast.makeText(MainActivity.this, "Regitered Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Could not register. Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
*/
    @Override
    public void onClick(View view) {
        /*
        if(view == buttonRegister) {
            registerUser();
        }

        if(view == textViewSignin){
            // Will open login activity here
        }*/
    }

}
