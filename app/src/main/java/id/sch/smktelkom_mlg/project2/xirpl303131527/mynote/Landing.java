package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.intro.IntroActivity;

public class Landing extends AppCompatActivity {

    public static Activity LandA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        LandA = this;

        Button btLogin = (Button) findViewById(R.id.buttonLogLanding);
        Button btRegister = (Button) findViewById(R.id.buttonRegLanding);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Landing.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Landing.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}
