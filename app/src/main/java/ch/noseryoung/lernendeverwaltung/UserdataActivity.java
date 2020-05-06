package ch.noseryoung.lernendeverwaltung;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;

public class UserdataActivity extends AppCompatActivity {

    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        userDao = MainActivity.getUserDao();

        TextView firstnameTextView = findViewById(R.id.newuser_firstnamePlainText);
        TextView lastnameTextView = findViewById(R.id.newuser_lastnamePlainText);

        Bundle intent = getIntent().getExtras();
        int userId = intent.getInt(UserlistActivity.EXTRA_USER_ID);
        User user = userDao.getById(userId);

        firstnameTextView.setText(user.getFirstName());
        lastnameTextView.setText(user.getLastName());

        setPhotoImageView(user.getPicture());
    }

    private void setPhotoImageView(String photoName){
        File userPhotoFile = getUserPhotoFromGallery(photoName);

        if(userPhotoFile == null) return;

        String userPhotoPath = userPhotoFile.getPath();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        final Bitmap userPhotoBitmap = BitmapFactory.decodeFile(userPhotoPath, options);

        ImageView userPhoto = findViewById(R.id.newUser_userPhoto);
        userPhoto.setImageBitmap(userPhotoBitmap);
    }

    private File getUserPhotoFromGallery(String photoName){
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        for (File file : storageDir.listFiles()) {
            if (photoName.equals(file.getName())) {
                return file;
            }
        }
        return null;
    }
}
