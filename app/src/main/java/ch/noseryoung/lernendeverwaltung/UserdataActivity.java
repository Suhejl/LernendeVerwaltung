package ch.noseryoung.lernendeverwaltung;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserdataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        TextView firstnameTextView = findViewById(R.id.newuser_firstnamePlainText);
        TextView lastnameTextView = findViewById(R.id.newuser_lastnamePlainText);

        Bundle intent = getIntent().getExtras();
        String[] getFirstAndLastname = intent.getStringArray(UserlistActivity.EXTRA_USER);
        firstnameTextView.setText(getFirstAndLastname[0]);
        lastnameTextView.setText(getFirstAndLastname[1]);
    }
}
