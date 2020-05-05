package ch.noseryoung.lernendeverwaltung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button seeApprenticeButton = findViewById(R.id.home_seeApprenticesButton);
        seeApprenticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserlist();
            }
        });
    }

    private void openUserlist() {
        Intent intend = new Intent(this, UserlistActivity.class);
        startActivity(intend);
    }
}
