package com.example.mahad.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = StartActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //This line will change the title from Lab# to the current activity's name
        //setTitle(ACTIVITY_NAME);

        Log.i(ACTIVITY_NAME, "In OnCreate()");

        Button chatButton = findViewById(R.id.startChat);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent
                startActivity(new Intent(StartActivity.this, ChatWindow.class));

            }
        });


        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 10);

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==10){
            Log.i(ACTIVITY_NAME, "return to StartActivity. OnResult");
        }
        if(resultCode== Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            Toast toast = Toast.makeText(getBaseContext(), messagePassed, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    protected void onResume(){
        super.onResume(); // Should always call the superclass method first
        Log.i(ACTIVITY_NAME, "In OnResume()");
    }

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In OnStart()");
    }

    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In OnPause()");
    }

    protected void onStop (){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In OnStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In OnDestroy()");
    }
}
