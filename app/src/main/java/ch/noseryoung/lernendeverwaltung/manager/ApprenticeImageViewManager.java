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

/**
 * Manager class that has the logic for the images in the filesystem
 */
public class ApprenticeImageViewManager {

    // Is needed to access the picture directory
    private Context context;
    private static final String TAG = "ApprenticeImageManager";

    public ApprenticeImageViewManager(Context context) {
        this.context = context;
    }

    /**
     * gets the apprentice image path and returns it as Bitmap
     * @param photoName
     * @return
     */
    public Bitmap getApprenticePhotoAsBitmap(String photoName) {
        File apprenticePhotoFile = getApprenticePhotoFileFromGallery(photoName);

        if (apprenticePhotoFile == null) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.account_circle);
            return ((BitmapDrawable) drawable).getBitmap();
        }

        String apprenticePhotoPath = apprenticePhotoFile.getPath();

        BitmapFactory.Options options = new BitmapFactory.Options();

        // Image gets smaller to save space
        options.inSampleSize = 8;
        return BitmapFactory.decodeFile(apprenticePhotoPath, options);
    }

    /**
     * Reads the pictures directory and gets the image file that is equal to the photo name given
     * @param photoName
     * @return
     */
    private File getApprenticePhotoFileFromGallery(String photoName) {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            for (File file : storageDir.listFiles()) {
                if (photoName.equals(file.getName())) {
                    return file;
                }
            }
        } catch (NullPointerException nullPointerEx) {
            Log.w(TAG, nullPointerEx.getMessage() + " --- No files found");
        }
        return null;
    }
}
