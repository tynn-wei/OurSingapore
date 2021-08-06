package com.example.oursingaporetynnwei;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    EditText etID, etName, etDesc, etSqkm;
    Button btnCancel, btnUpdate, btnDelete;
    RatingBar rb;
    TextView tvEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_third));


        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        etID = (EditText) findViewById(R.id.etID);
        etName = (EditText) findViewById(R.id.etName);
        etDesc = (EditText) findViewById(R.id.etDescription);
        etSqkm = (EditText) findViewById(R.id.etSqkm);
        rb = (RatingBar) findViewById(R.id.ratingBar3);
        tvEnd = (TextView) findViewById(R.id.textViewEnd);

        Intent i = getIntent();
        final Island currentIsland = (Island) i.getSerializableExtra("island");

        etID.setText(currentIsland.getId()+"");
        etName.setText(currentIsland.getName());
        etDesc.setText(currentIsland.getDesc());
        etSqkm.setText(currentIsland.getSqkm()+"");
        rb.setRating(currentIsland.getStars());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                currentIsland.setName(etName.getText().toString().trim());
                currentIsland.setDesc(etDesc.getText().toString().trim());
                int sqKm = 0;
                try {
                    sqKm = Integer.valueOf(etSqkm.getText().toString().trim());
                } catch (Exception e){
                    Toast.makeText(ThirdActivity.this, "Invalid area", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentIsland.setSqkm(4);



                currentIsland.setStars((int)rb.getRating());
                int result = dbh.updateIsland(currentIsland);
                if (result>0){
                    Toast.makeText(ThirdActivity.this, "Island updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);

                myBuilder.setTitle("Demo 2 Buttons Dialog");
                myBuilder.setMessage("Select one of the buttons below");
                myBuilder.setCancelable(false);

                //configure 'positive' button
                myBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvEnd.setText("You have selected positive");
                    }
                });

                myBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvEnd.setText("You have selected negative");
                    }
                });

                DBHelper dbh = new DBHelper(ThirdActivity.this);
                int result = dbh.deleteIsland(currentIsland.getId());
                if (result>0){
                    Toast.makeText(ThirdActivity.this, "Island deleted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}