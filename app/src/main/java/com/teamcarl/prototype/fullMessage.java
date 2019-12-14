package com.teamcarl.prototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class fullMessage extends Activity {

    Button btnReply, btnBack, btnDelete;
    String msgSubject, msgBody, msgFromUser;
    User currentUser;
    TextView subject, body;
    int messageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        DataAccess db = new DataAccess();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_message);
        msgSubject = intent.getExtras().getString("MessageInfoSubject");
        msgBody = intent.getExtras().getString("MessageInfoBody");
        messageId = Integer.parseInt(intent.getExtras().getString("MessageInfoId"));
        currentUser = (User) intent.getExtras().getSerializable("MessageInfoCurrentUser");
        msgFromUser = intent.getExtras().getString("MessageInfoFromUser");
        subject = (TextView) findViewById(R.id.subject);
        body = (TextView) findViewById(R.id.body);
        subject.setText(msgSubject);
        body.setText(msgBody);

        // set message as read because we are now reading current message id
        String query = "UPDATE MESSAGE " +
                "SET [read] = 1 " +
                "Where messageid = " + messageId;
        db.executeNonQuery(query);

        // reply to message, start reply
        btnReply=(Button)findViewById(R.id.reply);
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fullMessage.this, NewMessage.class);
                intent.putExtra("MessageInfoSubject", "RE: " + msgSubject);
                intent.putExtra("MessageInfoBody", msgBody);
                intent.putExtra("MessageInfoId", Integer.toString(messageId));
                intent.putExtra("MessageInfoReceiverUser", msgFromUser);
                intent.putExtra("MessageInfoCurrentUser", currentUser);
                startActivity(intent);
            }
        });

        // back button functionality
        btnBack=(Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // delete message functionality, make message NOT ACTIVE
        /*btnDelete=(Button)findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update message to be not active
                DataAccess db = new DataAccess();
                String query = "UPDATE MESSAGE " +
                        "SET [active] = 0 " +
                        "WHERE messageid = " + messageId;
                try {
                    String result = db.executeNonQuery(query);
                    // if error or no rows updated then error deleting
                    if (result.contains(("error")) || Integer.valueOf(result) < 1) {
                        Toast.makeText(getApplicationContext(), "Error deleting message...", Toast.LENGTH_SHORT).show();
                    }
                    else // message deleted successfully
                    {
                        Toast.makeText(getApplicationContext(), "Message deleted successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(fullMessage.this, Messages.class);
                        intent.putExtra("User", currentUser);
                        startActivityForResult(intent, 1);
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error deleting message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }
}
