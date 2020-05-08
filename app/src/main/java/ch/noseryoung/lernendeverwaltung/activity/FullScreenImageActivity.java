package ch.noseryoung.lernendeverwaltung.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.lernendeverwaltung.R;
import ch.noseryoung.lernendeverwaltung.manager.ApprenticeImageViewManager;
import ch.noseryoung.lernendeverwaltung.repository.Apprentice;
import ch.noseryoung.lernendeverwaltung.repository.ApprenticeDao;

/**
 * Class that handles the actions on the view, where the profilpicture of the apprentice is displayed.
 */
public class FullScreenImageActivity extends AppCompatActivity {
    // Database connection
    private ApprenticeDao apprenticeDao;
    private ApprenticeImageViewManager apprenticeImageViewManager;

    /**
     * Creates the view and displays it to the apprentice.
     * Gets picture depending on the content of callingActivityIntent and the if-clause.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apprenticeDao = MainActivity.getApprenticeDao();
        apprenticeImageViewManager = new ApprenticeImageViewManager(this);

        Intent callingActivityIntent = getIntent();

        // If a apprenticeId was given as extra in callingActivityIntent
        if (callingActivityIntent.hasExtra(ApprenticelistActivity.EXTRA_APPRENTICE_ID)) {
            int apprenticeID = callingActivityIntent.getIntExtra(ApprenticelistActivity.EXTRA_APPRENTICE_ID, 0);
            setFullScreenImageById(apprenticeID);
        } else {
            Uri apprenticePhotoUri = callingActivityIntent.getData();
            setFullScreenImageByUri(apprenticePhotoUri);
        }

    }

    /**
     * Gets profilepicture of a specific apprentice, out of the picture directory.
     * This method gets the picture of the apprentice with the id given as parameter.
     *
     * @param apprenticeID
     */
    private void setFullScreenImageById(int apprenticeID) {
        ImageView fullScreenImageView = findViewById(R.id.fullScreenImage_ApprenticePhoto);
        Apprentice apprentice = apprenticeDao.getById(apprenticeID);

        if (apprentice != null) {
            Bitmap apprenticePhotoBitmap = apprenticeImageViewManager.getApprenticePhotoAsBitmap(apprentice.getPicture());

            if (apprenticePhotoBitmap != null) {
                fullScreenImageView.setImageBitmap(apprenticePhotoBitmap);
            }
        }
    }

    /**
     * Shows the picture in the Form for creating a new apprentice.
     * Since there is no entry of the apprentice, the picture cant be found by id,
     * thats why we give the uri of the picture as a parameter.
     *
     * @param apprenticePhotoUri
     */
    private void setFullScreenImageByUri(Uri apprenticePhotoUri) {
        ImageView fullScreenImageView = findViewById(R.id.fullScreenImage_ApprenticePhoto);
        fullScreenImageView.setImageURI(apprenticePhotoUri);
    }

    /**
     * Method that handles the apprentice input in the navbar.
     * Since we only have a go back icon, this method destroys the activity.
     *
     * @param item
     * @return
     */
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
