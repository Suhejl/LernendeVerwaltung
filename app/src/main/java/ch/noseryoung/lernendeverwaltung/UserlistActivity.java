package ch.noseryoung.lernendeverwaltung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.noseryoung.lernendeverwaltung.repository.User;
import ch.noseryoung.lernendeverwaltung.repository.UserDao;

public class UserlistActivity extends AppCompatActivity implements ApprenticeAdapter.OnListItemClickListener{

    public static final String EXTRA_USER = "ch.noseryoung.lernendeverwaltung.EXTRA_USER";

    List<User> apprentices = new ArrayList<>();
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //gets dao from MainActivity
        userDao = MainActivity.getUserDao();

        loadList();

        Button seeApprenticeButton = findViewById(R.id.userlist_createApprenticeButton);
        seeApprenticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewUser();
            }
        });
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
        ApprenticeAdapter mAdapter = new ApprenticeAdapter(apprentices, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
    }

    @Override
    public void onItemClick(int position) {
        String[] firstAndLastname = new String[2];
        firstAndLastname[0] = apprentices.get(position).getFirstName();
        firstAndLastname[1] = apprentices.get(position).getLastName();
        Intent intent = new Intent(this, UserdataActivity.class);
        Bundle extras = new Bundle();
        extras.putStringArray(EXTRA_USER, firstAndLastname);
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void openNewUser() {
        Intent intend = new Intent(this, NewUserActivity.class);
        startActivity(intend);
    }
}