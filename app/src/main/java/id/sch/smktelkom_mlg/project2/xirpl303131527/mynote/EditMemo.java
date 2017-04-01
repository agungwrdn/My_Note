package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditMemo extends AppCompatActivity {
    private EditText etContentMemo;
    private TextView tvChar;

    private String mMemoKey;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mDatabase;

    private final TextWatcher etContentMemoWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            tvChar.setText(String.valueOf(s.length()) + " " + getResources().getString(R.string.charac));
        }

        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);

        setTitle(getResources().getString(R.string.edit_note));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();
        mDatabase = mDB.getReference().child("memo").child(mAuth.getCurrentUser().getUid().toString());

        etContentMemo = (EditText) findViewById(R.id.editTextContentedit);
        etContentMemo.addTextChangedListener(etContentMemoWatcher);
        tvChar = (TextView) findViewById(R.id.textViewCharedit);

        mMemoKey = getIntent().getExtras().getString("note_key");
        Log.d("FirebaseCounter", mMemoKey);

        fillData();
    }

    private void fillData() {
        mDatabase.child(mMemoKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String noteContent = (String) dataSnapshot.child("content").getValue();

                etContentMemo.setText(noteContent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_memo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            updateNote();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.action_delete) {
            deleteNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.del))
                .setMessage(getResources().getString(R.string.del_message))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.yes_option), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.child(mMemoKey).removeValue();

                        finish();
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_saved), Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton(getResources().getString(R.string.cancel_option), null).show();
    }

    private void updateNote() {
        String content = etContentMemo.getText().toString();

        if (TextUtils.isEmpty(content)) {
            return;
        }

        mDatabase.child(mMemoKey).child("content").setValue(content);

        Toast.makeText(getApplicationContext(), getResources().getString(R.string.note_saved), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        if (etContentMemo.length() > 0) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.alert))
                    .setMessage(getResources().getString(R.string.back_alert))
                    .setCancelable(true)
                    .setPositiveButton(getResources().getString(R.string.yes_option), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_saved), Toast.LENGTH_SHORT).show();
                            onSuperBackPressed();
                        }
                    }).setNegativeButton(getResources().getString(R.string.cancel_option), null).show();
        } else {
            onSuperBackPressed();
        }
    }

    public void onSuperBackPressed() {
        super.onBackPressed();
    }
}
