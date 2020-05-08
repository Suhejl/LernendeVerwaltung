package ch.noseryoung.lernendeverwaltung.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.lernendeverwaltung.R;

/**
 * Class which serves as a superclass, extended by the activities,
 * that are in need of the menu icon in the top right corner of the navbar.
 */
public class BaseMenuActivity extends AppCompatActivity {

    /**
     * Injects the data from the Resources.menu.main_menu file into the menu,
     * as soon as it is opened by the apprentice.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Defines the actions taken, depending on the selected part of the menu
     * In our situation, there is only one available.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.aboutus) {
            Intent intend = new Intent(this, AboutUsActivity.class);
            startActivity(intend);
            return true;
        }
        return false;
    }
}
