package ch.noseryoung.lernendeverwaltung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;

public class NewUserActivity extends AppCompatActivity {

    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        userDao = MainActivity.getUserDao();

        Button seeApprenticeButton = findViewById(R.id.newUser_button);
        seeApprenticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();
            }
        });
    }

    private void createNewUser() {
        EditText firstNameField = findViewById(R.id.editText);
        EditText lastNameField = findViewById(R.id.editText2);

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();

        userDao.insertUser(new User(firstName, lastName, "none"));

        finish();
    }
}
