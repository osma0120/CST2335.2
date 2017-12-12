package com.example.mahad.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter;


import java.util.ArrayList;


public class ChatWindow extends Activity {

    Button bt;
    ListView lt;
    EditText et;
    final ArrayList<String> chatMessages = new ArrayList<>();
    SQLiteDatabase db;
    ChatDatabaseHelper cdh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        bt = findViewById(R.id.sendbutton);
        lt = findViewById(R.id.listviewCW);
        et = findViewById(R.id.editTextCW);

        //loginButton =findViewById(R.id.loginButton);


        // setting the data source for the listview to be a new ChatAdapter
        final ChatAdapter messageAdapter = new ChatAdapter(ChatWindow.this);

        cdh = new ChatDatabaseHelper(ChatWindow.this);
        db = cdh.getWritableDatabase();


        Cursor results  = db.query(false, ChatDatabaseHelper.TABLE_NAME, new String []{ChatDatabaseHelper.KEY_MESSAGE}, null , null,null, null,null,null);


        //esults.moveToFirst();

        while(results.moveToNext()){
            String message = results.getString
                    (results.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
            Log.i("CHATWINDOW", "Sql Contained message " + message);

            Log.i("CHATWINDOW", "Cursor's column count=" +
                    results.getColumnCount());

            chatMessages.add(message);

           // results.moveToNext();


        }

        results.close();
        lt.setAdapter(messageAdapter);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(ChatDatabaseHelper.KEY_MESSAGE, et.getText().toString());
                db.insert(ChatDatabaseHelper.TABLE_NAME, "NullColumnName",contentValues);

                Log.i("CHATWINDOW", "Adding" + et.getText().toString());
                chatMessages.add(et.getText().toString());
                messageAdapter.notifyDataSetChanged();//restarts the process of getCount()/getView()
                et.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        db.close();
    }
    public class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return chatMessages.size();
        }

        public String getItem(int position) {
            return chatMessages.get(position);

        }

        public View getView(int position, View convertVIew, ViewGroup parent) {


            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }
            else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;

        }

    }


}



