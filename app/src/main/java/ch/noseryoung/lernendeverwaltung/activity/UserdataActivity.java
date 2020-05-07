package ch.noseryoung.lernendeverwaltung.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.lernendeverwaltung.R;
import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;
import ch.noseryoung.lernendeverwaltung.manager.UserImageViewManager;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserdataActivity extends AppCompatActivity {

    private UserDao userDao;
    private UserImageViewManager userImageViewManager;
    private static final String TAG = "UserdataActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        userDao = MainActivity.getUserDao();
        userImageViewManager = new UserImageViewManager(this);

        TextView firstnameTextView = findViewById(R.id.userdata_firstnamePlainText);
        TextView lastnameTextView = findViewById(R.id.userdata_lastnamePlainText);
        final CircleImageView userPhoto = findViewById(R.id.userdata_userPhoto);
        try {
            Bundle bundle = getIntent().getExtras();
            int userId = bundle.getInt(UserlistActivity.EXTRA_USER_ID);
            User user = userDao.getById(userId);


            firstnameTextView.setText(user.getFirstName());
            lastnameTextView.setText(user.getLastName());

            Bitmap userPhotoBitmap = userImageViewManager.getUserPhotoAsBitmap(user.getPicture());


            userPhoto.setImageBitmap(userPhotoBitmap);
        } catch (NullPointerException nullex) {
            Log.w(TAG, nullex.getMessage());
        }
    }
}
