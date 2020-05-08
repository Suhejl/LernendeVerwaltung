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

import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.lernendeverwaltung.R;
import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;
import ch.noseryoung.lernendeverwaltung.manager.UserImageViewManager;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserdataActivity extends AppCompatActivity {

    private static final String TAG = "UserdataActivity";
    public static final String EXTRA_USER_IDD = "ch.noseryoung.lernendeverwaltung.activity.EXTRA_USER_IDD";

    private UserDao userDao;
    private UserImageViewManager userImageViewManager;

    private User selectedApprentice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        userDao = MainActivity.getUserDao();
        userImageViewManager = new UserImageViewManager(this);


        TextView firstnameTextView = findViewById(R.id.userdata_firstname);
        TextView lastnameTextView = findViewById(R.id.userdata_lastname);
        final CircleImageView userPhoto = findViewById(R.id.userdata_userPhoto);
        try {
            Bundle bundle = getIntent().getExtras();
            int apprenticeID = bundle.getInt(UserlistActivity.EXTRA_USER_ID);
            selectedApprentice = userDao.getById(apprenticeID);


            firstnameTextView.setText(selectedApprentice.getFirstName());
            lastnameTextView.setText(selectedApprentice.getLastName());

            Bitmap userPhotoBitmap = userImageViewManager.getUserPhotoAsBitmap(selectedApprentice.getPicture());

            userPhoto.setImageBitmap(userPhotoBitmap);

            if (selectedApprentice != null) {
                userPhoto.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        openFullscreenApprenticePhoto();
                    }
                });
            }

        } catch (NullPointerException nullex) {
            Log.w(TAG, nullex.getMessage());
        }
    }

    private void openFullscreenApprenticePhoto() {
        Intent fullscreenIntent = new Intent(this, FullScreenImageActivity.class);
        fullscreenIntent.putExtra(UserlistActivity.EXTRA_USER_ID, selectedApprentice.getId());
        startActivity(fullscreenIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.aboutus) {
            Intent intend = new Intent(this, AboutUsActivity.class);
            startActivity(intend);
            return true;
        }
        return false;
    }
}
