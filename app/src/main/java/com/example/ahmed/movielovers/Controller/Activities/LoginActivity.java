package com.example.ahmed.movielovers.Controller.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmed.movielovers.Model.User;
import com.example.ahmed.movielovers.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText userName, password;
    Button login, signUp;
    SignInButton loginWithGmail;
    private GoogleSignInClient mGoogleSignInClient;
    final int signInRequstCode = 50;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = findViewById(R.id.login_user_name_et);
        password = findViewById(R.id.login_password_et);
        login = findViewById(R.id.login_button);
        loginWithGmail = findViewById(R.id.login_with_gmail);
        signUp = findViewById(R.id.login_sign_up_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = userName.getText().toString();
                final String pass = password.getText().toString();
                if (!name.equals("") && !pass.equals("")) {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<HashMap<String, Object>> usersData = (List<HashMap<String, Object>>) dataSnapshot.child("users").getValue();
                            for (int i = 0; i < usersData.size(); i++) {
                                if (name.equals(usersData.get(i).get("userName") + "") && pass.equals(usersData.get(i).get("password") + "")) {
                                    User user = dataSnapshot.child("users").child(i + "").getValue(User.class);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("user_account", user.getEmail());
                                    editor.apply();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                                } else if (i == usersData.size() - 1) {
                                    Toast.makeText(getApplicationContext(), "You are not a user in this application pleas sign up", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        loginWithGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, signInRequstCode);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(getApplicationContext(), SignUp.class);
                startActivity(signUp);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == signInRequstCode) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.i("user email", account.getEmail());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user_account", account.getEmail());
                editor.apply();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                Log.i("Sign in Failure", "Google sign in failed", e);
            }
        }
    }
}
