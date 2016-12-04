package com.wordpress.httpstheredefiningproductions.phonefinder;


/*
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/


import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;

import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class finder extends Activity  {


    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    int text_index;



//used to unmute the phone's audio system
public static AudioManager mAudioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finder);

        //nothing to do here except set the content view

        prefs = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);
        editor = prefs.edit();

        text_index = prefs.getInt("index", 0);




    }



    //start the service by clicking the "start" button
    public void onClickStartService(View V)
    {


        //create the text file
        //generateNoteOnSD(getApplicationContext(), "talk" + Integer.toString(text_index), "yolo");
        Toast.makeText(getApplicationContext(), "Recording", Toast.LENGTH_SHORT).show();
        //used to start the recorder intent this will allow the app to continually listen for the phrase



        text_index = text_index + 1;
        editor.putInt("index", text_index);
        editor.commit();

        Intent i = new Intent(this, recorder.class);
        i.putExtra("index", text_index);

        startService(i);










    }


    //Stop the started service with this code
    public void onClickStopService(View V)
    {
        //activate the audiomanager in order to control the audio of the system



         stopService(new Intent(this, recorder.class));

    }

//when the app is returned from after being put in the background
    @Override
    public void onResume(){
        super.onResume();
       //we want to stop app because when the user goes to app, most likely they will want to stop app
         stopService(new Intent(this, recorder.class));



    }

    @Override
    public void onBackPressed() {
        // your code.
        Intent myIntent = new Intent(getApplicationContext(), main.class);
        startActivityForResult(myIntent, 0);

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
            Toast.makeText(context, "Recording", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
