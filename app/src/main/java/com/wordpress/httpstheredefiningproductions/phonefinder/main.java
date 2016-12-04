package com.wordpress.httpstheredefiningproductions.phonefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by mikeabelar on 12/3/16.
 */

public class main  extends Activity {

    public Button summaries;
    public Button listen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        summaries = (Button) findViewById(R.id.summaries);
        listen = (Button) findViewById(R.id.listen);

        //nothing to do here except set the content view
        listen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), finder.class);
                startActivityForResult(myIntent, 0);
            }
        });

        summaries.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), summaries.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

}
