package ch.noseryoung.lernendeverwaltung.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.lernendeverwaltung.R;
import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;
import ch.noseryoung.lernendeverwaltung.manager.UserImageViewManager;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserdataActivity extends AppCompatActivity {

    private static final String TAG = "UserdataActivity";

    //Userdata which are shown
    private User selectedApprentice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        //database connection
        UserDao userDao = MainActivity.getUserDao();
        //returns picture depending on the string given to it
        UserImageViewManager userImageViewManager = new UserImageViewManager(this);


        TextView firstnameTextView = findViewById(R.id.userdata_firstname);
        TextView lastnameTextView = findViewById(R.id.userdata_lastname);
        CircleImageView userPhoto = findViewById(R.id.userdata_userPhoto);

        try {
            //fetch user by given userid in bundle
            Bundle bundle = getIntent().getExtras();
            int apprenticeID = bundle.getInt(UserlistActivity.EXTRA_USER_ID);
            selectedApprentice = userDao.getById(apprenticeID);

            //checks if a user with given id exists in database
            if (selectedApprentice != null) {
                firstnameTextView.setText(selectedApprentice.getFirstName());
                lastnameTextView.setText(selectedApprentice.getLastName());

                Bitmap userPhotoBitmap = userImageViewManager.getUserPhotoAsBitmap(selectedApprentice.getPicture());
                userPhoto.setImageBitmap(userPhotoBitmap);

                userPhoto.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        openFullscreenApprenticePhoto();
                    }
                });
            }else{
                Log.w(TAG, "User with id: " + apprenticeID + " does not exist");
            }

        } catch (NullPointerException nullex) {
            Log.w(TAG, nullex.getMessage());
        }
    }
    //opens new activity, that shows the profile picture in fullscreen
    private void openFullscreenApprenticePhoto() {
        Intent fullscreenIntent = new Intent(this, FullScreenImageActivity.class);
        fullscreenIntent.putExtra(UserlistActivity.EXTRA_USER_ID, selectedApprentice.getId());
        startActivity(fullscreenIntent);
    }
    //Menu Methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.aboutus) {
            Intent intend = new Intent(this, AboutUsActivity.class);
            startActivity(intend);
            return true;
        }
        return false;
    }
}
