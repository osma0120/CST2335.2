package com.example.mahad.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = LoginActivity.class.getSimpleName();
    Button loginButton;
    public static final String PREFS  = "MyPrefsFile";
    EditText emailtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailtext =findViewById(R.id.email);

        loginButton =findViewById(R.id.loginButton);

        final SharedPreferences sharedPreferences = getSharedPreferences(PREFS , Context.MODE_PRIVATE);
        emailtext.setText(sharedPreferences.getString("Email", "email@domain.com"));

        //This line will change the title from Lab# to the current activity's name
        //setTitle(ACTIVITY_NAME);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                sharedPreferences.getString("defaultemail", "email@domain.com");

                String emailaddress = emailtext.getText().toString();

                editor.putString("Email", emailaddress);
                editor.commit();


                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);

            }
        });






        Log.i(ACTIVITY_NAME, "In OnCreate()");
    }
    protected void onResume(){
        super.onResume(); // Should always call the superclass method first
        Log.i(ACTIVITY_NAME, "In OnResume()");
    }

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In OnStart)");
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
