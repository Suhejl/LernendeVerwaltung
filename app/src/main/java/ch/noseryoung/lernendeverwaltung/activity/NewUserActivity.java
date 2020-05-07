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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.noseryoung.lernendeverwaltung.R;
import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewUserActivity extends AppCompatActivity {

    private UserDao userDao;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String PROVIDER_PATH = "ch.noseryoung.lernendeverwaltung.provider";
    private static final String TAG = "NewUserActivity";

    private Uri currentPhotoUri;
    private String currentPhotoName;

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
                selectImage();
            }
        });

        CircleImageView apprenticePhoto = findViewById(R.id.newUser_userPhoto);

        apprenticePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPhotoUri == null) {
                    selectImage();
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
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = BitmapFactory.decodeFile(picturePath);
                Log.w(TAG, picturePath);
                photo.setImageBitmap(thumbnail);

            }
        }
    }

    private void selectImage() {
        final String takePhotoOption = "Foto aufnehmen";
        final String chooseGalleryOption = "Foto aus Galerie auswählen";
        final String cancelOption = "Abbrechen";

        final String[] options = {takePhotoOption, chooseGalleryOption, cancelOption};
        AlertDialog.Builder builder = new AlertDialog.Builder(NewUserActivity.this);
        builder.setTitle("Foto hinzufügen");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (takePhotoOption.equals(options[which])) {
                    openCamera();
                } else if (chooseGalleryOption.equals(options[which])) {
                    openGallery();
                } else if (cancelOption.equals(options[which])) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_LernendeFoto";
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