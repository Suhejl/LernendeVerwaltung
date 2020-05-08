package ch.noseryoung.lernendeverwaltung.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.noseryoung.lernendeverwaltung.R;
import ch.noseryoung.lernendeverwaltung.repository.Apprentice;
import ch.noseryoung.lernendeverwaltung.repository.ApprenticeDao;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Class that represents the view where the form is to create a new Apprentice.
 */
public class NewApprenticeActivity extends BaseMenuActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    // Path for the created Class that extends FileProvider
    private static final String PROVIDER_PATH = "ch.noseryoung.lernendeverwaltung.provider";
    private static final String TAG = "NewApprenticeActivity";

    private Uri currentPhotoUri;
    private String currentPhotoName;

    // Database connection
    private ApprenticeDao apprenticeDao;

    /**
     * Creates the view and displays it to the apprentice.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_apprentice);
        getSupportActionBar().setTitle(R.string.view_new_apprentice_title);

        apprenticeDao = MainActivity.getApprenticeDao();

        // OnClickListener that creates new apprentice and closes NewApprentice Activity to navigate to Apprenticelist
        Button seeApprenticesButton = findViewById(R.id.newApprentice_createButton);
        seeApprenticesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewApprentice();
            }

        });

        // OnClickListener that opens camera
        Button photoButton = findViewById(R.id.newApprentice_photoButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        // OnClickListener if picture was taken, the picture opens up in fullscreen, else the camera is opened
        CircleImageView apprenticePhoto = findViewById(R.id.newApprentice_apprenticePhoto);
        apprenticePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPhotoUri == null) {
                    openCamera();
                } else {
                    openFullscreenApprenticePhoto();
                }
            }
        });
    }

    /**
     * If an implicit intent is called, this overriden method gets the request code of the intent.
     * With the result code you can check if the picture was taken and confirmed
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView photo = findViewById(R.id.newApprentice_apprenticePhoto);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                photo.setImageURI(currentPhotoUri);
            }
        } else {
            // When the picture hasn't been taken, the apprentice has no picture
            currentPhotoUri = null;
            currentPhotoName = null;
        }
    }

    /**
     * Opens an implicit intent to the camera.
     * First an image file is created and the FileProvider gets the URI of the image file.
     */
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File imageFile = null;
            try {
                // Image file is created with the createImageFile() method
                imageFile = createImageFile();
            } catch (IOException ex) {
                Log.e(TAG, "Error occurred while creating the File");
            }

            if (imageFile != null) {
                Context context = getApplicationContext();
                currentPhotoUri = FileProvider.getUriForFile(
                        context, PROVIDER_PATH, imageFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    /**
     * Creates an image file in the directory pictures of the installed application.
     * Name of created image is named by a time stamp.
     * To create the file, the FileProvider is used for secure file sharing
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_LernendeFoto_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,        // Filename without extension
                ".jpg",         // File extension
                storageDir      // Directory, where file should be saved
        );
        currentPhotoName = image.getName();
        return image;
    }

    /**
     * Creates a new apprentice after validating the form and destroys this activity
     */
    private void createNewApprentice() {
        EditText firstNameField = findViewById(R.id.newApprentice_firstnamePlainText);
        EditText lastNameField = findViewById(R.id.newApprentice_lastnamePlainText);

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();

        if (checkForSize(firstName) && checkForSize(lastName)) {
            if (currentPhotoName == null || currentPhotoName.trim().length() == 0) {
                currentPhotoName = "none";
            }
            apprenticeDao.insertApprentice(new Apprentice(firstName, lastName, currentPhotoName));
            finish();
        }
    }

    /**
     * Gets executed when profile picture of apprentice is clicked.
     * Opens FullScreenImageActivity
     */
    private void openFullscreenApprenticePhoto() {
        if (currentPhotoUri != null) {
            Intent fullscreenIntent = new Intent(this, FullScreenImageActivity.class);
            fullscreenIntent.setData(currentPhotoUri);
            startActivity(fullscreenIntent);
        }
    }

    /**
     * Checks the entries in the form. The firstname and the lastname have to be between 1 and 49 characters to return true.
     * @param name
     * @return
     */
    private boolean checkForSize(String name) {
        // Represents the current instance of this activity and is used to make Toasts
        Context context = getApplicationContext();

        if (name.trim().length() > 50) {
            Toast toast = Toast.makeText(context, name + " ist zu lange. Bitte geben Sie ein Namen unter 50 Zeichen ein.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return false;
        } else if (name.trim().length() == 0) {
            Toast toast = Toast.makeText(context, "Bitte geben Sie ein Wert im leeren Feld aus", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return false;
        } else {
            return true;
        }
    }
}