package edu.kylegilmartin.teamup.LoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.kylegilmartin.teamup.MainActivity;
import edu.kylegilmartin.teamup.R;
import edu.kylegilmartin.teamup.admin.AdminMain;

public class Login extends AppCompatActivity {

    private TextView register,forgotpassword;
    private EditText editTextEmail,editTextPassword;
    private Button signin;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();


        signin = findViewById(R.id.signIn);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               userLogin();
            }
        });

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        forgotpassword = findViewById(R.id.forgotPassword);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, ForgotPassword.class);
                startActivity(i);
            }
        });
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


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
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6) {
            editTextPassword.setError("Password mast be at last 6 characters long");
            editTextPassword.findFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        progressBar.setVisibility(View.GONE);
                        checkUserAccessLevel(AuthResult.getUser().getUid());


                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        user.sendEmailVerification();
                        Toast.makeText(Login.this, "Email verification has been sent", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Failed to login", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkUserAccessLevel() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Boolean.parseBoolean("1")) != null) {
                    Intent i = new Intent(Login.this, AdminMain.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                }

            }
        });
    };
}