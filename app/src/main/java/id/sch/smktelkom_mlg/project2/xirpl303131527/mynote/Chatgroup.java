package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chatgroup extends AppCompatActivity {
    private Button sent;
    private TextView chat;
    private EditText etChat;
    private String user_name,room_name;
    private DatabaseReference root;
    private String temp_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatgroup);

        sent = (Button) findViewById(R.id.sent);
        chat = (TextView) findViewById(R.id.textView2);
        etChat = (EditText) findViewById(R.id.editText);

        //user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        root = FirebaseDatabase.getInstance().getReference().child("group").child(room_name);
        setTitle(" Room - "+room_name);
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key=root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);

                Map<String, Object> map2 = new HashMap<String, Object>();
                //map2.put("name",user_name);
                map2.put("msg", etChat.getText().toString());

                message_root.updateChildren(map2);
            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ngomong_kambek_dewe(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ngomong_kambek_dewe(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String chat_msg, chat_username;
    private void ngomong_kambek_dewe(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){
            chat_msg = (String)((DataSnapshot)i.next()).getValue();
            //chat_username = (String)((DataSnapshot)i.next()).getValue();

            chat.append(chat_msg+" \n");
        }

    }
}
