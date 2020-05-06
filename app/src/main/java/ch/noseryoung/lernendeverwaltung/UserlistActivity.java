package ch.noseryoung.lernendeverwaltung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserlistActivity extends AppCompatActivity implements ApprenticeAdapter.OnListItemClickListener{

    public static final String EXTRA_TEXT = "ch.noseryoung.lernendeverwaltung.EXTRA_TEXT";


    private ArrayList<String> apprentices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apprentices.add("Gianluca Asani");
        apprentices.add("Gianluca Umanchandram");
        apprentices.add("Gianluca Umanchandramvvvv");
        apprentices.add("Umanchandramvsdfgvbsvsdfvsdvdsvfsgvfsvsfvfvfdvdfvf Umanchandramvsdfgvbsvsdfvsdvdsvfsgvfsvsfvfvfdvdfvf");
        apprentices.add("Gianluca Umanchandram5");
        apprentices.add("Gianluca 6Umanchandram");
        apprentices.add("Gianluca 7Umanchandram");
        apprentices.add("Gianluca 8Umanchandram");
        apprentices.add("Gianluca 9Umanchandram");
        apprentices.add("Gianluca 10Umanchandram");
        apprentices.add("Gianluca 11Umanchandram");
        apprentices.add("Gianluca 12Umanchandram");

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

        // Set listener onClick to navigate to new user activity
        Button seeApprenticeButton = findViewById(R.id.userlist_createApprenticeButton);
        seeApprenticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewUser();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        String[] firstAndLastname = apprentices.get(position).split(" ");
        Intent intent = new Intent(this, UserDataActivity.class);
        Bundle extras = new Bundle();
        extras.putStringArray(EXTRA_TEXT, firstAndLastname);
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void openNewUser() {
        Intent intend = new Intent(this, NewUserActivity.class);
        startActivity(intend);
    }
}