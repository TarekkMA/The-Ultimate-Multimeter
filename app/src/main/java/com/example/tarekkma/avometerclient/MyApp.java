package com.example.tarekkma.avometerclient;

import android.app.Application;
import android.util.Log;
import android.view.View;

import com.example.tarekkma.avometerclient.events.BluetoothConnectionEvent;
import com.example.tarekkma.avometerclient.events.BluetoothStateChangedEvent;
import com.example.tarekkma.avometerclient.events.ReadingsReceivedEvent;

import org.greenrobot.eventbus.EventBus;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by tarekkma on 4/6/17.
 */

public class MyApp extends Application {

    private static final String TAG = "MyApp";

    BluetoothUtils bluetoothUtils;

    @Override
    public void onCreate() {
        super.onCreate();

        bluetoothUtils = new BluetoothUtils(new BluetoothSPP(this));

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                //.setDefaultFontPath("fonts/digital-7.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );



    }



}
