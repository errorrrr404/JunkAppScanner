package com.example.appscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    ListView lvHarmfulAppList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        lvHarmfulAppList = findViewById(R.id.lvHarmfulAppList);

        ArrayList<String> fetchedArrayList = getIntent().getStringArrayListExtra("foundHarmfulApps");
        String[] fetchedArrayListToArray = new String[fetchedArrayList.size()];
        if (fetchedArrayList.size() == 0){
            String convertedString = "No Harmful Apps found..!! :D";
            fetchedArrayListToArray = new String[]{convertedString};
        }
        else {
            fetchedArrayListToArray = fetchedArrayList.toArray(fetchedArrayListToArray);
        }

        lvHarmfulAppList.setAdapter(new ArrayAdapter<String>(Activity2.this, android.R.layout.simple_list_item_1, fetchedArrayListToArray));
    }
}