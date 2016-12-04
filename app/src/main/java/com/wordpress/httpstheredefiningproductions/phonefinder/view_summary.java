package com.wordpress.httpstheredefiningproductions.phonefinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;


import java.io.FileWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


import com.knowledgebooks.nlp.KeyPhraseExtractionAndSummary;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mikeabelar on 12/3/16.
 */

public class view_summary extends Activity {

    TextView original;
    TextView summarized;
    String original_text = "";
    Button delete;

    String file_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_summary);


        original = (TextView) findViewById(R.id.original_text);
        summarized = (TextView) findViewById(R.id.summarized_text);

        delete = (Button) findViewById(R.id.delete);

        //get text

        Intent iin= getIntent();
        Bundle bundle = iin.getExtras();

        if(bundle!=null)
        {
            original_text = (String) bundle.get("text");
            original.setText(original_text);

            file_number = (String) bundle.get("number");
        }

        if (original_text != "") {
            KeyPhraseExtractionAndSummary e = new KeyPhraseExtractionAndSummary(original_text);

            summarized.setText(e.getSummary());

        }

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                generateNoteOnSD(getApplicationContext(), "talk" + file_number,"IGNORE FILE");

                Intent intent = new Intent(getApplicationContext(), summaries.class);

                startActivity(intent);

            }
        });












    }


    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "talks");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName + ".txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






