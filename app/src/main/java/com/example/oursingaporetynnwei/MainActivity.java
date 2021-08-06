package com.example.oursingaporetynnwei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etName, etDesc, etSqkm;
    Button btnInsert, btnShowList;
    RatingBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_main));

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDescription);
        etSqkm = findViewById(R.id.etSqkm);
        btnInsert = findViewById(R.id.btnInsertIsland);
        btnShowList = findViewById(R.id.btnShowList);
        rb = findViewById(R.id.ratingBar);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                if (name.length() == 0 || desc.length() == 0){
                    Toast.makeText(MainActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                    return;
                }

                String sqKm_str = etSqkm.getText().toString().trim();
                int sqKm = Integer.valueOf(sqKm_str);

                int stars = (int) rb.getRating();



                DBHelper dbh = new DBHelper(MainActivity.this);
                long result = dbh.insertIsland(name, desc, sqKm, stars);

                if (result != -1) {
                    Toast.makeText(MainActivity.this, "Song inserted", Toast.LENGTH_LONG).show();
                    etName.setText("");
                    etDesc.setText("");
                    etSqkm.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                }


            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });

    }




}