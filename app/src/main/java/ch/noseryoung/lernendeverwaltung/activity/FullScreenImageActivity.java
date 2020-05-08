package ch.noseryoung.lernendeverwaltung.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import ch.noseryoung.lernendeverwaltung.R;
import ch.noseryoung.lernendeverwaltung.manager.UserImageViewManager;
import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;

public class FullScreenImageActivity extends AppCompatActivity {

    private UserDao userDao;
    private UserImageViewManager userImageViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        userDao = MainActivity.getUserDao();
        userImageViewManager = new UserImageViewManager(this);

        Intent callingActivityIntent = getIntent();

        if (callingActivityIntent != null) {
            if (callingActivityIntent.hasExtra(UserlistActivity.EXTRA_USER_ID)) {
                int apprenticeID = callingActivityIntent.getIntExtra(UserlistActivity.EXTRA_USER_ID, 0);
                setFullScreenImageById(apprenticeID);
            } else{
                Uri apprenticePhotoUri = callingActivityIntent.getData();
                setFullScreenImageByUri(apprenticePhotoUri);
            }
        }
    }

    private void setFullScreenImageById(int apprenticeID){
        ImageView fullScreenImageView = findViewById(R.id.fullScreenImage_ApprenticePhoto);
        User apprentice = userDao.getById(apprenticeID);

        if (apprentice != null) {
            Bitmap apprenticePhotoBitmap = userImageViewManager.getUserPhotoAsBitmap(apprentice.getPicture());

            if (apprenticePhotoBitmap != null) {
                fullScreenImageView.setImageBitmap(apprenticePhotoBitmap);
            }
        }
    }

    private void setFullScreenImageByUri(Uri apprenticePhotoUri){
        ImageView fullScreenImageView = findViewById(R.id.fullScreenImage_ApprenticePhoto);
        fullScreenImageView.setImageURI(apprenticePhotoUri);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
