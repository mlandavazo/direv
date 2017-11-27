package com.direv.direv;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.direv.direv.DataClasses.User;
import com.direv.direv.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Malachi on 11/24/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private Context mContext;
    private String firstname, lastname, email, username, password, passwordconfirm;
    private EditText mFirstname, mLastname, mEmail, mPassword, mPasswordConfirm, mUsername;
    private TextView loadingPleaseWait;
    private Button btnRegister;
    private ProgressBar mProgressBar;
    private CheckBox mCheckBox;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private String append = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mContext = RegisterActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);
        Log.d(TAG, "onCreate: started.");

        initWidgets();
        setupFirebaseAuth();
        init();
    }

    private void init(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname = mFirstname.getText().toString();
                lastname = mLastname.getText().toString();
                email = mEmail.getText().toString();
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();
                passwordconfirm = mPasswordConfirm.getText().toString();

                if(checkInputs(firstname, lastname, email, username, password, passwordconfirm) && mCheckBox.isChecked()){
                    if(password.equals(passwordconfirm)) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        loadingPleaseWait.setVisibility(View.VISIBLE);

                        firebaseMethods.registerNewEmail(email, password, username);
                    }
                    else{
                        Toast.makeText(mContext, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (!mCheckBox.isChecked()){
                    Toast.makeText(mContext, "You must agree to the terms.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkInputs(String firstname, String lastname, String email, String username, String password, String passwordconfirm){
        Log.d(TAG, "checkInputs: checking inputs for null values.");
        if(firstname.equals("") || lastname.equals("") || email.equals("") || username.equals("") || password.equals("") || passwordconfirm.equals("")){
            Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /**
     * Initialize the activity widgets
     */
    private void initWidgets(){
        Log.d(TAG, "initWidgets: Initializing Widgets.");
        mFirstname = (EditText) findViewById(R.id.editTextFirstName);
        mLastname = (EditText) findViewById(R.id.editTextLastName);
        mEmail = (EditText) findViewById(R.id.editTextEmailSignup);
        mUsername = (EditText) findViewById(R.id.editTextUsername);
        btnRegister = (Button) findViewById(R.id.buttonSignUp);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        loadingPleaseWait = (TextView) findViewById(R.id.pleaseWait);
        mPassword = (EditText) findViewById(R.id.editTextPassword);
        mPasswordConfirm = (EditText) findViewById(R.id.editTextPasswordConfirm);
        mContext = RegisterActivity.this;
        mProgressBar.setVisibility(View.GONE);
        loadingPleaseWait.setVisibility(View.GONE);
        mCheckBox = (CheckBox) findViewById(R.id.TermscheckBox);

    }

    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string if null.");

        if(string.equals("")){
            return true;
        }
        else{
            return false;
        }
    }

     /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Check is @param username already exists in teh database
     * @param username
     */
    private void checkIfUsernameExists(final String username) {
        Log.d(TAG, "checkIfUsernameExists: Checking if  " + username + " already exists.");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    if (singleSnapshot.exists()){
                        Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + singleSnapshot.getValue(User.class).getUsername());
                        append = myRef.push().getKey().substring(3,10);
                        Log.d(TAG, "onDataChange: username already exists. Appending random string to name: " + append);
                    }
                }

                String mUsername = "";
                mUsername = username + append;

                Log.d(TAG, "first: " + firstname);
                Log.d(TAG, "last: " + lastname);
                Log.d(TAG, "email: " + email);
                Log.d(TAG, "username: " + username);
                //add new user to the database
                firebaseMethods.addNewUser(firstname, lastname, email, mUsername, "");

                Toast.makeText(mContext, "Signup successful. Sending verification email.", Toast.LENGTH_SHORT).show();

                mAuth.signOut();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            checkIfUsernameExists(username);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    finish();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
