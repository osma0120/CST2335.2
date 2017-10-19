package com.example.mahad.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    Button bt;
    ListView lt;
    EditText et;
    final ArrayList<String> chatMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        bt = findViewById(R.id.sendbutton);
        lt = findViewById(R.id.listviewCW);
        et = findViewById(R.id.editTextCW);

        //loginButton =findViewById(R.id.loginButton);


        // setting the data source for the listview to be a new ChatAdapter
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        lt.setAdapter(messageAdapter);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String message = et.getText().toString();0
                chatMessages.add(et.getText().toString());
                messageAdapter.notifyDataSetChanged();//restarts the process of getCount()/getView()
                et.setText("");
            }
        });
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



