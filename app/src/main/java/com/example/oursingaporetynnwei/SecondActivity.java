package com.example.oursingaporetynnwei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Island> islandList;
    CustomAdapter adapter;
    int requestCode = 9;
    Button btn5Stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle(getTitle().toString() + " ~ " +  getResources().getText(R.string.title_activity_second));

        lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);

        DBHelper dbh = new DBHelper(this);
        islandList = dbh.getAllIsland();
        dbh.close();

        //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, songList);
        //lv.setAdapter(adapter);

        adapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, islandList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("island", islandList.get(position));
                startActivityForResult(i, requestCode);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                islandList.clear();
                islandList.addAll(dbh.getAllIslandsByStars(5));
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == this.requestCode && resultCode == RESULT_OK){
            DBHelper dbh = new DBHelper(this);
            islandList.clear();
            islandList.addAll(dbh.getAllIsland());
            dbh.close();
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}