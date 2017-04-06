package id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.intro;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.LoginActivity;
import id.sch.smktelkom_mlg.project2.xirpl303131527.mynote.R;


public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        addSlide(AppIntroFragment.newInstance("Note", "You can create Note for something",
                R.mipmap.note,
                Color.parseColor("#51e2b7")));

        addSlide(AppIntroFragment.newInstance("Target", "Target make your future is amazing",
                R.mipmap.todolist,
                Color.parseColor("#f442c8")));

        addSlide(AppIntroFragment.newInstance("Chat", "You can chat whit other user",
                R.mipmap.chat,
                Color.parseColor("#c10e0b")));

        showStatusBar(false);
        setBarColor(Color.parseColor("#333639"));
        setSeparatorColor(Color.parseColor("#2196F3"));
    }

    @Override
    public void onDonePressed() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void onSkipPressed(Fragment currentFragment) {
        Toast.makeText(this, "on skip clicked", Toast.LENGTH_SHORT).show();
    }

    public void onSlideChanged(){
        Toast.makeText(this,"on Slide changed", Toast.LENGTH_SHORT).show();
    }
}
