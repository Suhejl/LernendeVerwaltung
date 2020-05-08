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
            int apprenticeID = bundle.getInt(UserlistMenuActivity.EXTRA_USER_ID);
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
        fullscreenIntent.putExtra(UserlistMenuActivity.EXTRA_USER_ID, selectedApprentice.getId());
        startActivity(fullscreenIntent);
    }
}
