package edu.kylegilmartin.teamup.UserDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import edu.kylegilmartin.teamup.LoginRegister.Login;
import edu.kylegilmartin.teamup.R;
import edu.kylegilmartin.teamup.admin.AdminMain;

public class UserProfile extends AppCompatActivity {
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        frameLayout = findViewById(R.id.changeProfile);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserProfile.this, EditUserProfile.class);
                startActivity(i);
                finish();
            }
        });
    }
}