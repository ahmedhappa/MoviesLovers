package com.example.ahmed.movielovers.Controller.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmed.movielovers.Model.User;
import com.example.ahmed.movielovers.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class SignUp extends AppCompatActivity {
    EditText userName, password, email, age;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userName = findViewById(R.id.sign_up_username);
        password = findViewById(R.id.sign_up_password);
        email = findViewById(R.id.sign_up_email);
        age = findViewById(R.id.sign_up_age);
        signUp = findViewById(R.id.sign_up_button);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();
        final int[] userId = new int[1];
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Object> objects = (List<Object>) dataSnapshot.child("users").getValue();
                if (objects == null) {
                    userId[0] = 0;
                    Log.e("why","leh");
                } else {
                    userId[0] = objects.size();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                String pass = password.getText().toString();
                String mail = email.getText().toString();
                if (!name.equals("") && !pass.equals("") && !mail.equals("") && !age.getText().toString().equals("")) {
                    Log.e("a77oooo", userId[0] +"");
                    int ag = Integer.parseInt(age.getText().toString());
                    User user = new User(userId[0], ag, name, pass, mail);
                    databaseReference.child("users").child(userId[0] + "").setValue(user);
                } else {
                    Toast.makeText(getApplicationContext(), "Pleas fill all the data", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
