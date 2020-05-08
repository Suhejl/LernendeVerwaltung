package ch.noseryoung.lernendeverwaltung.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.noseryoung.lernendeverwaltung.R;
import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewUserActivity extends BaseMenuActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String PROVIDER_PATH = "ch.noseryoung.lernendeverwaltung.provider";
    private static final String TAG = "NewUserActivity";

    private Uri currentPhotoUri;
    private String currentPhotoName;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        userDao = MainActivity.getUserDao();

        // Creates new user and closes NewUser Activity to navigate to Userlist
        Button seeApprenticeButton = findViewById(R.id.newUser_createButton);
        seeApprenticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();
            }

        });

        Button photoButton = findViewById(R.id.newUser_photoButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        CircleImageView apprenticePhoto = findViewById(R.id.newUser_userPhoto);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView photo = findViewById(R.id.newUser_userPhoto);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                photo.setImageURI(currentPhotoUri);
            }
        } else {
            currentPhotoUri = null;
        }
    }

    private void openCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File imageFile = null;
            try {
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

    private void createNewUser() {
        EditText firstNameField = findViewById(R.id.newUser_firstnamePlainText);
        EditText lastNameField = findViewById(R.id.newUser_lastnamePlainText);

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();

        if (checkForSize(firstName) && checkForSize(lastName)) {
            if (currentPhotoName == null || currentPhotoName.trim().length() == 0) {
                currentPhotoName = "none";
            }
            userDao.insertUser(new User(firstName, lastName, currentPhotoName));
            finish();
        }
    }

    private void openFullscreenApprenticePhoto() {
        if (currentPhotoUri != null) {
            Intent fullscreenIntent = new Intent(this, FullScreenImageActivity.class);
            fullscreenIntent.setData(currentPhotoUri);
            startActivity(fullscreenIntent);
        }
    }

    private boolean checkForSize(String name) {

        Context context = getApplicationContext();

        if (name.trim().length() >= 50) {
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