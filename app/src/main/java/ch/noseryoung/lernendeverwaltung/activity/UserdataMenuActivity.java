package ch.noseryoung.lernendeverwaltung.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ch.noseryoung.lernendeverwaltung.R;
import ch.noseryoung.lernendeverwaltung.manager.UserImageViewManager;
import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserdataMenuActivity extends BaseMenuActivity {

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
            int apprenticeID = bundle.getInt(UserlistMenuActivity.EXTRA_USER_ID);
            selectedApprentice = userDao.getById(apprenticeID);


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
            }

        } catch (NullPointerException nullex) {
            Log.w(TAG, nullex.getMessage());
        }
    }
    //opens new activity, that shows the profile picture in fullscreen
    private void openFullscreenApprenticePhoto() {
        Intent fullscreenIntent = new Intent(this, FullScreenImageActivity.class);
        fullscreenIntent.putExtra(UserlistMenuActivity.EXTRA_USER_ID, selectedApprentice.getId());
        startActivity(fullscreenIntent);
    }
}
