package com.example.tarekkma.avometerclient.events;

import android.support.annotation.Nullable;

import com.example.tarekkma.avometerclient.BluetoothUtils;

/**
 * Created by tarekkma on 4/7/17.
 */

public class BluetoothConnectionEvent {
    public final String name;
    public final String address;
    public final BluetoothUtils.Connection connection;

    public BluetoothConnectionEvent(BluetoothUtils.Connection connection,@Nullable String name, @Nullable String address) {
        this.name = name;
        this.address = address;
        this.connection = connection;
    }
}
