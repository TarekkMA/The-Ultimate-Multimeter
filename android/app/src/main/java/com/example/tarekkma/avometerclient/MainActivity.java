package com.example.tarekkma.avometerclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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


        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String firstTime = "LPKO";

        if(prefs.getBoolean(firstTime,true)){
            new MaterialDialog.Builder(this)
                    .content("للإستفادة من التطبيق يجب عليك الحصول علي الجهاز الخاص بالتطبيق" +
                            "\n" +
                            "\n" +
                            "للحصول علي الجهاز :\n" +
                            "01008659599 م/ حسام حسن" +
                            "\n" +
                            "01013658016 م/ طارق محمد")
                    .positiveText("حسناً")
                    .neutralText("عدم الإظهار مجدداً")
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            prefs.edit().putBoolean(firstTime,false).apply();
                        }
                    })
            .show();
        }

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
