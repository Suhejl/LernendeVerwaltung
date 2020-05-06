package ch.noseryoung.lernendeverwaltung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        TextView firstnameTextView = findViewById(R.id.userdata_firstnameTextView);
        TextView lastnameTextView = findViewById(R.id.userdata_lastnameTextView);

        Bundle intent = getIntent().getExtras();
        String[] getFirstAndLastname = intent.getStringArray(UserlistActivity.EXTRA_TEXT);
        firstnameTextView.setText(getFirstAndLastname[0]);
        lastnameTextView.setText(getFirstAndLastname[1]);

        Button backButton = findViewById(R.id.newUser_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserlist();
            }
        });
    }

    private void openUserlist() {
        finish();
    }
}
