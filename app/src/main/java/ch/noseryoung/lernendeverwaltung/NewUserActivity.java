package ch.noseryoung.lernendeverwaltung;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewUserActivity extends AppCompatActivity {

    UserDao userDao;

    public static final String EXTRA_PHOTOURL = "ch.noseryoung.lernendeverwaltung.EXTRA_PHOTOURL";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String PROVIDER_PATH = "ch.noseryoung.lernendeverwaltung.provider";

    private Uri currentPhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        userDao = MainActivity.getUserDao();

        Button seeApprenticeButton = findViewById(R.id.newUser_createButton);
        seeApprenticeButton.setOnClickListener(new View.OnClickListener() {

        Button photoButton = findViewById(R.id.newUser_photoButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        // Creates new user and closes NewUser Activity to navigate to Userlist
        Button backToUserlistButton = findViewById(R.id.newUser_backButton);
        backToUserlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView photo = findViewById(R.id.newUser_userLogo);

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
                Log.w("path from gallery..**..", picturePath);
                photo.setImageBitmap(thumbnail);

            }
        }
    }

    private void selectImage() {
        final String takePhotoOption = "Take Photo";
        final String chooseGalleryOption = "Choose from Gallery";
        final String cancelOption = "Cancel";

        final String[] options = {takePhotoOption, chooseGalleryOption, cancelOption};
        AlertDialog.Builder builder = new AlertDialog.Builder(NewUserActivity.this);
        builder.setTitle("Add Photo");
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
                Log.e("NewUser", "Error occurred while creating the File");
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

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, 		// Dateiname ohne Endung
                ".jpg",         // Dateiendung
                storageDir      // Verzeichnis, in welchem Datei gespeichert werden soll
        );

        return image;
    }

    private void createNewUser() {
        EditText firstNameField = findViewById(R.id.userdata_firstnameTextView);
        EditText lastNameField = findViewById(R.id.userdata_lastnameTextView);

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();

        if(checkForSize(firstName) && checkForSize(lastName)) {
            userDao.insertUser(new User(firstName, lastName, "none"));
            finish();
        }
    }

    private boolean checkForSize(String name){

        Context context = getApplicationContext();

        if(name.trim().length() >= 50){
            Toast toast = Toast.makeText(context,  name + " is too long. Enter a name below 50 characters", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return false;
        }else if(name.trim().length() == 0){
            Toast toast = Toast.makeText(context, "Please enter a value into the empty field", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            return false;
        }else{
            return true;
        }
    }
}
