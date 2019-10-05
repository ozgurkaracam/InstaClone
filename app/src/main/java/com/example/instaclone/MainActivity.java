package com.example.instaclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button buttonSignin,buttonSignup;
    TextView textView;
    EditText editEmail,editPassword;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth= FirebaseAuth.getInstance();
        buttonSignin=findViewById(R.id.buttonSignIn);
        buttonSignup=findViewById(R.id.buttonSignUp);
        editEmail=findViewById(R.id.editEmail);
        editPassword=findViewById(R.id.editPassword);
        textView=findViewById(R.id.textView3);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
            textView.setText(firebaseUser.getEmail());
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.createUserWithEmailAndPassword(editEmail.getText().toString(),editPassword.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Sign Up Successful!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this,FeedActivity.class));
                                    firebaseUser=firebaseAuth.getCurrentUser();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,task.getException().toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .addOnFailureListener(MainActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signInWithEmailAndPassword(editEmail.getText().toString(),editPassword.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Login Successful!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this,FeedActivity.class));
                                    firebaseUser=firebaseAuth.getCurrentUser();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,task.getException().toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .addOnFailureListener(MainActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
       if(currentUser!=null){
            startActivity(new Intent(MainActivity.this,FeedActivity.class));
        }
    }
}
