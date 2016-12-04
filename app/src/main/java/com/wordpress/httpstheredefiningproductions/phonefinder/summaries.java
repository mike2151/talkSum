package com.wordpress.httpstheredefiningproductions.phonefinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mikeabelar on 12/3/16.
 */

public class summaries extends Activity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    int text_index;


    @Override
    public void onBackPressed() {
        // your code.
        Intent myIntent = new Intent(getApplicationContext(), main.class);
        startActivityForResult(myIntent, 0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.summaries);

        prefs = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);
        editor = prefs.edit();

        text_index = prefs.getInt("index", 0);

        final ListView listview = (ListView) findViewById(R.id.listview);

        final ArrayList<String> list = new ArrayList<String>();

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);



        for (int i = 0; i < text_index+1; i++) {
            if (i != 0) {
                //now check if it is empty
                if (!fileIsEmpty(Integer.toString(i))){
                    list.add("talk " + Integer.toString(i));
                }
            }

        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                //get the number from the item
                String file_index = item.substring(5, item.length());


                File root = new File(Environment.getExternalStorageDirectory(), "talks");


                File file = new File(root, "talk" + file_index + ".txt");


                StringBuilder text = new StringBuilder();

                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;

                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    br.close();
                }
                catch (IOException e) {
                    //You'll need to add proper error handling here
                }

                Intent intent = new Intent(getApplicationContext(), view_summary.class);
                intent.putExtra("text", text.toString());
                intent.putExtra("number", file_index);
                startActivity(intent);



            }

        });





    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }
    }

    public boolean fileIsEmpty( String file_number) {
        File root = new File(Environment.getExternalStorageDirectory(), "talks");



        File file = new File(root, "talk" + file_number + ".txt");


        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        if (text.toString().contains("IGNORE FILE")) {

            return true;
        }
        else {
            return false;
        }
    }




}
