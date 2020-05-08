package ch.noseryoung.lernendeverwaltung.activity;

import android.os.Bundle;
import android.view.MenuItem;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.view_about_us_title);
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
