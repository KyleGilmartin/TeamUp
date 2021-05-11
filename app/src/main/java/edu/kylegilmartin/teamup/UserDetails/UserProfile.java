package edu.kylegilmartin.teamup.UserDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.kylegilmartin.teamup.LoginRegister.Login;
import edu.kylegilmartin.teamup.LoginRegister.User;
import edu.kylegilmartin.teamup.MainActivity;
import edu.kylegilmartin.teamup.R;
import edu.kylegilmartin.teamup.admin.AdminMain;

public class UserProfile extends AppCompatActivity {
    private FrameLayout frameLayout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menuAccount);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuHome:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menuFavourite:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menuSettings:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        frameLayout = findViewById(R.id.changeProfile);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserProfile.this, EditUserProfile.class);
                startActivity(i);
                finish();
            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        final TextView fullnameTextView = findViewById(R.id.userfullname);
        final TextView emailtextview = findViewById(R.id.emailuser);
        final TextView consoletypetextview = findViewById(R.id.consoleuser);
        final TextView nameidtextview = findViewById(R.id.nameiduser);
        final TextView kdtextview = findViewById(R.id.kduser);
        final TextView username = findViewById(R.id.usernameuser);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    String consoletype = userProfile.consoletype;
                    String kd = userProfile.kd;
                    String nameID = userProfile.nameid;
                    fullnameTextView.setText(fullName == fullName ? fullName : "No Data");
                    username.setText(fullName == fullName ? fullName : "No Data");
                    emailtextview.setText(email == email ? email : "No Data");
                    consoletypetextview.setText(consoletype == consoletype ? consoletype : "No Data");
                    nameidtextview.setText(nameID == nameID ? nameID : "No Data");
                    kdtextview.setText(kd == kd ? kd : "No Data");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Something bad happened!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}