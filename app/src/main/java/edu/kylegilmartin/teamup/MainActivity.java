package edu.kylegilmartin.teamup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.kylegilmartin.teamup.LoginRegister.Login;
import edu.kylegilmartin.teamup.LoginRegister.Register;
import edu.kylegilmartin.teamup.LoginRegister.User;

public class MainActivity extends AppCompatActivity {

    private Button logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.signOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

//        final TextView greetingTextView = findViewById(R.id.greeting);
//        final TextView fullnameTextView = findViewById(R.id.fullname);
//        final TextView emailTextView = findViewById(R.id.emailAddress);
//        final TextView ageTextView = findViewById(R.id.age);

//        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User userProfile = snapshot.getValue(User.class);
//                if(userProfile != null){
//                    String fullName = userProfile.fullName;
//                    String email = userProfile.email;
//                    String age = userProfile.age;
//
//                    greetingTextView.setText("Welcome, " + fullName + "!");
//                    fullnameTextView.setText(fullName);
//                    emailTextView.setText(email);
//                    ageTextView.setText(age);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this, "Something bad happened!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}