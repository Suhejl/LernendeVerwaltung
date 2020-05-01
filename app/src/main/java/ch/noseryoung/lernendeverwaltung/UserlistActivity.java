package ch.noseryoung.lernendeverwaltung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class UserlistActivity extends AppCompatActivity implements ApprenticeAdapter.OnListItemClickListener{

    private ArrayList apprentices = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apprentices.add("Gianluca Asani");
        apprentices.add("Gianluca Umanchandram");
        apprentices.add("Gianluca Umanchandramvvvv");
        apprentices.add("Umanchandram Umanchandram");
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
    }

    @Override
    public void onItemClick(int position) {
        Toast toast = Toast.makeText(this.getApplicationContext(), "clicked on " + apprentices.get(position ), Toast.LENGTH_SHORT );
        toast.show();
    }
}