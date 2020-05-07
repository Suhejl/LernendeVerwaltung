package ch.noseryoung.lernendeverwaltung.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import ch.noseryoung.lernendeverwaltung.R;

public class UserImageViewManager {

    private Context context;
    private static final String TAG = "UserImageViewManager";

    public UserImageViewManager(Context context) {
        this.context = context;
    }

    public Bitmap getUserPhotoAsBitmap(String photoName) {
        File userPhotoFile = getUserPhotoFileFromGallery(photoName);

        if (userPhotoFile == null) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.account_circle);
            return ((BitmapDrawable) drawable).getBitmap();
        }

        String userPhotoPath = userPhotoFile.getPath();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        return BitmapFactory.decodeFile(userPhotoPath, options);
    }

    private File getUserPhotoFileFromGallery(String photoName) {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            for (File file : storageDir.listFiles()) {
                if (photoName.equals(file.getName())) {
                    return file;
                }
            }
        } catch (NullPointerException nullPointerEx) {
            Log.w(TAG, nullPointerEx.getMessage());
        }
        return null;
    }
}
