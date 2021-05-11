package edu.kylegilmartin.teamup.UserDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.kylegilmartin.teamup.R;

public class EditUserProfile extends AppCompatActivity {
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        save = findViewById(R.id.BtnProfileSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditUserProfile.this, UserProfile.class);
                startActivity(i);
                finish();
            }
        });
    }
}