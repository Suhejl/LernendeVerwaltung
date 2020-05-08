package ch.noseryoung.lernendeverwaltung.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.lernendeverwaltung.R;
import ch.noseryoung.lernendeverwaltung.repository.ApprenticeDao;
import ch.noseryoung.lernendeverwaltung.repository.ApprenticeDatabase;

/**
 * Class that opens up the very first page of our application, the welcome page.
 */
public class MainActivity extends AppCompatActivity {
    // Database connection
    private static ApprenticeDao apprenticeDao;

    /**
     * Creates the view and displays it to the apprentice.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apprenticeDao = ApprenticeDatabase.getApprenticeDb(this).getApprenticeDao();

        Button seeApprenticeButton = findViewById(R.id.home_seeApprenticesButton);
        seeApprenticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openApprenticelist();
            }
        });
    }

    /**
     * Gives the datase connection to all other classes, which are in need of data.
     *
     * @return
     */
    public static ApprenticeDao getApprenticeDao() {
        return apprenticeDao;
    }

    /**
     * Opens new activity which displays the apprentice list
     */
    private void openApprenticelist() {
        Intent intend = new Intent(this, ApprenticelistActivity.class);
        startActivity(intend);
    }
}
