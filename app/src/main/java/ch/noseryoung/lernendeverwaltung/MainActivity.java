package ch.noseryoung.lernendeverwaltung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ch.noseryoung.lernendeverwaltung.repository.UserDao;
import ch.noseryoung.lernendeverwaltung.repository.UserDatabase;

public class MainActivity extends AppCompatActivity {

    private static UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDao = UserDatabase.getUserDb(this).getUserDao();

        Button seeApprenticeButton = findViewById(R.id.home_seeApprenticesButton);
        seeApprenticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserlist();
            }
        });
    }

    public static UserDao getUserDao() {
        return userDao;
    }

    private void openUserlist() {
        Intent intend = new Intent(this, UserlistActivity.class);
        startActivity(intend);
    }
}
