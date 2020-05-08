package ch.noseryoung.lernendeverwaltung.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.noseryoung.lernendeverwaltung.adapter.ApprenticeAdapter;
import ch.noseryoung.lernendeverwaltung.R;
import ch.noseryoung.lernendeverwaltung.manager.UserImageViewManager;
import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;

public class UserlistActivity extends AppCompatActivity implements ApprenticeAdapter.OnListItemClickListener {

    public static final String EXTRA_USER_ID = "ch.noseryoung.lernendeverwaltung.activity.EXTRA_USER_ID";

    private List<User> apprentices = new ArrayList<>();
    private UserDao userDao;
    private UserImageViewManager userImageViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //gets dao from MainActivity
        userDao = MainActivity.getUserDao();
        userImageViewManager = new UserImageViewManager(this);

        loadList();

        Button seeApprenticeButton = findViewById(R.id.userlist_createApprenticeButton);
        seeApprenticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewUser();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.aboutus) {
            Intent intend = new Intent(this, AboutUsActivity.class);
            startActivity(intend);
            return true;
        }
        return false;
    }

    private void loadList(){
        //loads list from database
        apprentices = userDao.getAll();

        RecyclerView recyclerView = findViewById(R.id.userlist_apprenticesList);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        ApprenticeAdapter mAdapter = new ApprenticeAdapter(apprentices, this, userImageViewManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
    }

    @Override
    public void onItemClick(int position) {
        User user = apprentices.get(position);
        Intent intent = new Intent(this, UserdataActivity.class);
        intent.putExtra(EXTRA_USER_ID, user.getId());
        startActivity(intent);
    }

    private void openNewUser() {
        Intent intend = new Intent(this, NewUserActivity.class);
        startActivity(intend);
    }
}