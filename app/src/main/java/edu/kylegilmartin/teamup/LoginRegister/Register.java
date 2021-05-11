package edu.kylegilmartin.teamup.LoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import edu.kylegilmartin.teamup.MainActivity;
import edu.kylegilmartin.teamup.R;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView banner, registerUser;
    private EditText editTextFullName,editTextAge,editTextEmail,editTextPassword;
    private ProgressBar processBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        editTextFullName = findViewById(R.id.fullname);
        editTextAge =  findViewById(R.id.age);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        processBar = findViewById(R.id.progressBar);
        registerUser = findViewById(R.id.registerUser);
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               registerUser();
            }
        });
        banner = findViewById(R.id.banner);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
            }
        });
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String fullname = editTextFullName.getText().toString().trim();
        String admin = "0";
        String kd = "0";
        String consoletype = "na";
        String nameid = "na";

        if(fullname.isEmpty()){
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(age.isEmpty()){
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
            return;
        }
        int in = Integer.valueOf(editTextAge.getText().toString());
        if(in < 15){
            editTextAge.setError("User must be 16 or older");
            editTextAge.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please prove valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.length() < 6) {
            editTextPassword.setError("Password mast be at last 6 characters long");
            editTextPassword.findFocus();
            return;
        }


        processBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(fullname,age,email,admin,kd,consoletype,nameid);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this, "User is registered", Toast.LENGTH_SHORT).show();
                                processBar.setVisibility(View.GONE);
                                Intent i = new Intent(Register.this, Login.class);
                                startActivity(i);
                            }else {
                                Toast.makeText(Register.this, "User is not registered", Toast.LENGTH_SHORT).show();
                                processBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else {
                    Toast.makeText(Register.this, "User is not registered", Toast.LENGTH_SHORT).show();
                    processBar.setVisibility(View.GONE);
                }
            }
        });


    }
}