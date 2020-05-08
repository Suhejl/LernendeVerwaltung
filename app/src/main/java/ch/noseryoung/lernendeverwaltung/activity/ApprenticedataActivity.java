package ch.noseryoung.lernendeverwaltung.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ch.noseryoung.lernendeverwaltung.R;
import ch.noseryoung.lernendeverwaltung.manager.ApprenticeImageViewManager;
import ch.noseryoung.lernendeverwaltung.repository.Apprentice;
import ch.noseryoung.lernendeverwaltung.repository.ApprenticeDao;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Class that represents the view where the data of a single apprentice is shown.
 */
public class ApprenticedataActivity extends BaseMenuActivity {

    private static final String TAG = "ApprenticedataActivity";

    // Apprenticedata which are shown
    private Apprentice selectedApprentice;

    /**
     * Creates the view and displays it to the apprentice.
     * Gets apprentice data out of bundle and displays it on the view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apprentice_data);
        getSupportActionBar().setTitle(R.string.view_apprentice_data_title);

        // Database connection
        ApprenticeDao apprenticeDao = MainActivity.getApprenticeDao();

        // Returns picture by name depending on the string given to it
        ApprenticeImageViewManager apprenticeImageViewManager = new ApprenticeImageViewManager(this);

        TextView firstnameTextView = findViewById(R.id.apprenticedata_firstname);
        TextView lastnameTextView = findViewById(R.id.apprenticedata_lastname);
        CircleImageView apprenticePhoto = findViewById(R.id.apprenticedata_apprenticePhoto);

        try {
            // Fetch apprentice by given apprentice id in bundle
            Bundle bundle = getIntent().getExtras();
            int apprenticeID = bundle.getInt(ApprenticelistActivity.EXTRA_APPRENTICE_ID);
            selectedApprentice = apprenticeDao.getById(apprenticeID);

            firstnameTextView.setText(selectedApprentice.getFirstName());
            lastnameTextView.setText(selectedApprentice.getLastName());

            Bitmap apprenticePhotoBitmap = apprenticeImageViewManager.getApprenticePhotoAsBitmap(selectedApprentice.getPicture());
            apprenticePhoto.setImageBitmap(apprenticePhotoBitmap);

            // OnClickListener opens image in fullscreen
            apprenticePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFullscreenApprenticePhoto();
                }
            });
        } catch (NullPointerException nullex) {
            Log.e(TAG, nullex.getMessage() + " --- On getting bundle apprentice id");
        }
    }

    /**
     * Opens new activity, that shows the profile picture in fullscreen
     */
    private void openFullscreenApprenticePhoto() {
        Intent fullscreenIntent = new Intent(this, FullScreenImageActivity.class);
        fullscreenIntent.putExtra(ApprenticelistActivity.EXTRA_APPRENTICE_ID, selectedApprentice.getId());
        startActivity(fullscreenIntent);
    }
}
