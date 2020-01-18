package com.example.tester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.buttonMain);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                goToRandomActivity();
            }

        });
    }

    private void goToRandomActivity() {
        Random rand = new Random();
        int select = rand.nextInt(3) + 1;
        switch(select) {
            case 1:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, ThirdActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, FourthActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

}
