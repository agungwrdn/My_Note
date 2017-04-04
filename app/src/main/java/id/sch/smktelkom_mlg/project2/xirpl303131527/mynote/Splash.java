package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {
    private String users = "";
    private FirebaseDatabase mDB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();

        if (!konek(this)) {
            new AlertDialog.Builder(Splash.this)
                    .setTitle(getResources().getString(R.string.failed))
                    .setMessage("No internet connection")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }

        if (mAuth.getCurrentUser() == null) {
            Intent i = new Intent(Splash.this, Landing.class);
            startActivity(i);
            finish();
        } else {
            handleLoading();
        }
    }

    public boolean konek(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    private void handleLoading() {
        DatabaseReference mDBuser = mDB.getReference().child("users").child(mAuth.getCurrentUser().getUid().toString());

        DatabaseReference dbUsername = mDBuser.child("email");
        dbUsername.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = dataSnapshot.getValue(String.class);

                if (!TextUtils.isEmpty(users)) {
                    Intent u = new Intent(Splash.this, MainActivity.class);
                    startActivity(u);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do nothing
            }
        });
    }
}
