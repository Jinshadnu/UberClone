package com.example.uberclone.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uberclone.R;
import com.example.uberclone.register.pojo.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    public Button button_register;
    public EditText editText_email,editText_name,editText_password,editText_phone;
    public String email,password,phone,name;

    public FirebaseAuth firebaseAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        editText_name=(EditText) findViewById(R.id.editTextTextPersonName);
        editText_email=(EditText) findViewById(R.id.editTextTextPersonEmail);
        editText_password=(EditText) findViewById(R.id.editTextTextPersonPassword);
        editText_phone=(EditText) findViewById(R.id.editTextTextPersonPhone);
        button_register=(Button)findViewById(R.id.button_register);


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=editText_email.getText().toString();
                phone=editText_phone.getText().toString();
                password=editText_password.getText().toString();
                name=editText_name.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user=new User();
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setPhone(phone);
                        user.setName(name);

                        databaseReference.child(user.getEmail()).setValue(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(),"Registration Succesfull",Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_LONG).show();

                            }
                        });


                    }
                });
            }
        });



    }
}