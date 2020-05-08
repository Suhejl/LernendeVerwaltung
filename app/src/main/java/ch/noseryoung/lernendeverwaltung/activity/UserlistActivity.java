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
import ch.noseryoung.lernendeverwaltung.manager.UserImageViewManager;
import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;

public class UserlistActivity extends BaseMenuActivity implements ApprenticeAdapter.OnListItemClickListener {

    public static final String EXTRA_USER_ID = "ch.noseryoung.lernendeverwaltung.activity.EXTRA_USER_ID";

    //list containing all users and their data
    private List<User> apprentices = new ArrayList<>();
    //database connection
    private UserDao userDao;
    //returns picture depending on the string given to it
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
    protected void onResume() {
        super.onResume();
        //reloads list after a new user was created
        loadList();
    }

    private void loadList(){
        //loads list from database
        apprentices = userDao.getAll();

        RecyclerView recyclerView = findViewById(R.id.userlist_apprenticesList);
        // uses this setting to improve performance if the changes are known
        // in content does not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        // uses a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specifies an adapter
        ApprenticeAdapter mAdapter = new ApprenticeAdapter(apprentices, this, userImageViewManager);
        recyclerView.setAdapter(mAdapter);
    }
    //Opens Activity with user data of clicked entry
    //Method is overridden from ApprenticeAdapter
    @Override
    public void onItemClick(int position) {
        User user = apprentices.get(position);
        Intent intent = new Intent(this, UserdataActivity.class);
        intent.putExtra(EXTRA_USER_ID, user.getId());
        startActivity(intent);
    }
    //Opens Activity with form for creating a new user
    private void openNewUser() {
        Intent intend = new Intent(this, NewUserActivity.class);
        startActivity(intend);
    }
}