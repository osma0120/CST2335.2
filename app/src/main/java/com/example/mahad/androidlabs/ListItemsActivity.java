package com.example.mahad.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {
    protected static final String ACTIVITY_NAME = ListItemsActivity.class.getSimpleName();
    static final int REQUEST_IMAGE_CAPTURE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        //This line will change the title from Lab# to the current activity's name
        //setTitle(ACTIVITY_NAME);


        ImageButton imageButton = findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });


        final Switch switchButton = findViewById(R.id.switch1);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CharSequence text = "Switch is Off";
                int duration = Toast.LENGTH_LONG;
                if (switchButton.isChecked()) {
                    text = "Switch is On";
                    duration = Toast.LENGTH_SHORT;
                }

                Toast toast = Toast.makeText(getBaseContext(), text, duration);
                toast.show();
            }
        });


        final CheckBox checkBox = findViewById(R.id.checkBox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                builder.setMessage(R.string.dialog_message)
                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("Response", getString(R.string.ToastMessage));
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        })

                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })

                        .show();

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode ==RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageButton imageButton = findViewById(R.id.imageButton);
            imageButton.setImageBitmap(imageBitmap);
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

    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In OnDisplay()");
    }


}
