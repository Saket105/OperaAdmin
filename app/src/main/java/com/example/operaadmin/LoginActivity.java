package com.example.operaadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText edt_email, edt_pass;
    Button signIn;
    FirebaseAuth mAuth;
    SpinKitView progressBar;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        signIn = findViewById(R.id.login_btn);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.spin_kit);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        onStart();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.requestFocus();
                progressBar.setFocusable(true);
                if (TextUtils.isEmpty(edt_email.getText().toString())){
                    edt_email.setError("Required");
                    edt_email.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;

                }else if (TextUtils.isEmpty(edt_pass.getText().toString())){
                    edt_pass.setError("Required!");
                    edt_pass.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else if (edt_pass.getText().toString().length()<8){
                    edt_pass.setError("Not the same password, please try again");
                    edt_pass.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(edt_email.getText().toString(),edt_pass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseDatabase.getInstance().getReference("admin")
                                        .child(FirebaseAuth.getInstance().getUid())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()){
                                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(LoginActivity.this, "Welcome Back!", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }else {
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(LoginActivity.this, "You are not an Admin!", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                            }else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "You don't have access for this app", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}