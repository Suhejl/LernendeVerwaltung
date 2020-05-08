package ch.noseryoung.lernendeverwaltung.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.lernendeverwaltung.R;

/**
 * Easter Egg Activity.
 */
public class AboutUsActivity extends AppCompatActivity {

     /**
     * Creates the view and displays it to the apprentice.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }
}
