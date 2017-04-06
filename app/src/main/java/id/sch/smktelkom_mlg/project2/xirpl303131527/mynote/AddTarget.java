package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTarget extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase mDB;
    private DatabaseReference mDBtarget, mDBtargetUser;
    private FirebaseAuth mAuth;
    private Long jumlahData;
    private Integer currentPostId;
    private int mYear, mMonth, mDay;

    private String dbCurrentUser;

    private EditText etTitleTarget;
    private EditText sTarget;
    private TextView tvChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_target);

        sTarget = (EditText) findViewById(R.id.tgl);
        etTitleTarget = (EditText) findViewById(R.id.editText);
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();

        dbCurrentUser = mAuth.getCurrentUser().getUid().toString();

        mDBtarget = mDB.getReference().child("target");
        mDBtargetUser = mDBtarget.child(dbCurrentUser);

        mDBtargetUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jumlahData = dataSnapshot.getChildrenCount() - 1;
                currentPostId = jumlahData.intValue() + 1;
                String strValue = jumlahData.toString();
                Boolean isDataNotExist = false;
                while (isDataNotExist == false) {
                    Boolean cleanCheck = dataSnapshot.child(currentPostId.toString()).exists();
                    if (cleanCheck) {
                        Log.d("FirebaseCounter", "Child with ID " + currentPostId.toString() + " already Exists! +1");
                        currentPostId += 1;
                    } else {
                        Log.d("FirebaseCounter", "Child with ID " + currentPostId.toString() + " Available to Use!");
                        isDataNotExist = true;
                    }
                }
                Log.d("FirebaseCounter", strValue);
                Log.d("FirebaseCounter", "Next Post Should be : " + currentPostId.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FirebaseError", databaseError.getMessage());
            }
        });

        sTarget.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save_note) {
            uploadTarget();
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadTarget() {
        String title = etTitleTarget.getText().toString();
        String due = sTarget.getText().toString();
        Date date = new Date();
        String time = DateFormat.getDateTimeInstance().format(date);

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(due)) {
            return;
        }

        // Database Reference for memo
        DatabaseReference dbtarget = mDBtargetUser.child(currentPostId.toString());

        Log.d("FirebaseCounter", mAuth.getCurrentUser().getUid());
        dbtarget.child("title").setValue(title);
        dbtarget.child("due").setValue(due);
        dbtarget.child("time").setValue(time);

        Toast.makeText(getApplicationContext(), getResources().getString(R.string.note_saved), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        if (etTitleTarget.length() > 0) {
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

    private void onSuperBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        sTarget.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
