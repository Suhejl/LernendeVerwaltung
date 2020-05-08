package ch.noseryoung.lernendeverwaltung.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.noseryoung.lernendeverwaltung.R;
import ch.noseryoung.lernendeverwaltung.adapter.ApprenticeAdapter;
import ch.noseryoung.lernendeverwaltung.manager.ApprenticeImageViewManager;
import ch.noseryoung.lernendeverwaltung.repository.Apprentice;
import ch.noseryoung.lernendeverwaltung.repository.ApprenticeDao;

public class ApprenticelistActivity extends BaseMenuActivity implements ApprenticeAdapter.OnListItemClickListener {

    public static final String EXTRA_APPRENTICE_ID = "ch.noseryoung.lernendeverwaltung.activity.EXTRA_APPRENTICE_ID";

    // List containing all apprentices and their data
    private List<Apprentice> apprentices = new ArrayList<>();

    // Database connection
    private ApprenticeDao apprenticeDao;

    // Returns picture depending on the string given to it
    private ApprenticeImageViewManager apprenticeImageViewManager;

    /**
     * Creates the view and displays it to the apprentice.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apprenticelist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Gets dao from MainActivity
        apprenticeDao = MainActivity.getApprenticeDao();
        apprenticeImageViewManager = new ApprenticeImageViewManager(this);

        loadList();

        // OnClickListener opens activity for new apprentice
        Button createApprenticeButton = findViewById(R.id.apprenticelist_createApprenticeButton);
        createApprenticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewApprentice();
            }
        });
    }

    /**
     * Reloads list after a new apprentice was created
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadList();
    }

    /**
     * Injects apprentice data into the list
     */
    private void loadList(){
        // Loads list from database
        apprentices = apprenticeDao.getAll();

        RecyclerView recyclerView = findViewById(R.id.apprenticelist_apprenticesList);

        // Uses this setting to improve performance if the changes are known
        // in content does not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        // uses a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specifies an adapter
        ApprenticeAdapter mAdapter = new ApprenticeAdapter(apprentices, this, apprenticeImageViewManager);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     *  Opens Activity with apprentice data of clicked entry
     *  Method is overridden from ApprenticeAdapter
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Apprentice apprentice = apprentices.get(position);
        Intent intent = new Intent(this, ApprenticedataActivity.class);
        intent.putExtra(EXTRA_APPRENTICE_ID, apprentice.getId());
        startActivity(intent);
    }

    /**
     * Opens Activity with form for creating a new apprentice
     */
    private void openNewApprentice() {
        Intent intend = new Intent(this, NewApprenticeActivity.class);
        startActivity(intend);
    }
}