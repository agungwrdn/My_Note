package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.fragment.About;
import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.fragment.Goals;
import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.fragment.My;
import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.fragment.Reminder;
import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.fragment.chat;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvUsernameDrawer;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mDBuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation_bottom);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();
                        changePage(id);

                        return true;
                    }
                });
        View hView = navigationView.getHeaderView(0);
        tvUsernameDrawer = (TextView) hView.findViewById(R.id.textViewUsernameDrawer);
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();
        mDBuser = mDB.getReference().child("users");

        retrieveData();
        changePage(R.id.nav_note);
        navigationView.setCheckedItem(R.id.nav_note);
    }

    private void changePage(int id) {

        Fragment fragment = null;
        if (id == R.id.nav_note) {
            fragment = new My();
        } else if (id == R.id.nav_reminder) {
            fragment = new Reminder();
        } else if (id == R.id.nav_mygoals) {
            fragment = new Goals();
        } else if (id == R.id.nav_chat) {
            fragment = new chat();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitNow();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
            Fragment fragment = null;

        if (id == R.id.nav_about) {
            fragment = new About();
        } else if (id == R.id.nav_logout) {
            fragment = new My();

            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.logout))
                    .setMessage(getResources().getString(R.string.logout_message))
                    .setCancelable(true)
                    .setPositiveButton(getResources().getString(R.string.yes_option), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAuth.signOut();

                            Intent logout = new Intent(MainActivity.this, Splash.class);
                            startActivity(logout);
                            finish();
                        }
                    }).setNegativeButton(getResources().getString(R.string.cancel_option), null).show();

        }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitNow();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void retrieveData() {
        //Mengambil data username yang login
        DatabaseReference userName = mDBuser.child(mAuth.getCurrentUser().getUid()).child("displayName");
        userName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.getValue(String.class);
                tvUsernameDrawer.setText(username);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
