package ch.noseryoung.lernendeverwaltung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;

public class NewUserActivity extends AppCompatActivity {

    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        userDao = MainActivity.getUserDao();

        Button seeApprenticeButton = findViewById(R.id.newUser_createButton);
        seeApprenticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();
            }
        });
    }

    private void createNewUser() {
        EditText firstNameField = findViewById(R.id.userdata_firstnameTextView);
        EditText lastNameField = findViewById(R.id.userdata_lastnameTextView);

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();

        if(checkForSize(firstName) && checkForSize(lastName)) {
            userDao.insertUser(new User(firstName, lastName, "none"));
            finish();
        }
    }

    private boolean checkForSize(String name){

        Context context = getApplicationContext();

        if(name.trim().length() >= 50){
            Toast toast = Toast.makeText(context,  name + " is too long. Enter a name below 50 characters", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return false;
        }else if(name.trim().length() == 0){
            Toast toast = Toast.makeText(context, "Please enter a value into the empty field", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return false;
        }else{
            return true;
        }
    }
}
