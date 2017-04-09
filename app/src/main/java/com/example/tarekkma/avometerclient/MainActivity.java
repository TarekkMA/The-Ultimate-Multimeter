package com.example.tarekkma.avometerclient;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.tarekkma.avometerclient.fragments.AboutFragment;
import com.example.tarekkma.avometerclient.fragments.BookFragment;
import com.example.tarekkma.avometerclient.fragments.DisplayFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private FrameLayout container;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_display:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, DisplayFragment.newInstance()).commit();
                    return true;
                case R.id.navigation_book:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, BookFragment.newInstance()).commit();
                    return true;
                case R.id.navigation_about:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, AboutFragment.newInstance()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (FrameLayout) findViewById(R.id.content);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, DisplayFragment.newInstance()).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
