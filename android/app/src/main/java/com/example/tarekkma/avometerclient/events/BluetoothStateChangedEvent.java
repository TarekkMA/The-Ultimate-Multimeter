package com.example.tarekkma.avometerclient.events;

import com.example.tarekkma.avometerclient.BluetoothUtils;

/**
 * Created by tarekkma on 4/7/17.
 */

public class BluetoothStateChangedEvent {
    public final BluetoothUtils.States state;

    public BluetoothStateChangedEvent(BluetoothUtils.States state) {
        this.state = state;
    }
}
