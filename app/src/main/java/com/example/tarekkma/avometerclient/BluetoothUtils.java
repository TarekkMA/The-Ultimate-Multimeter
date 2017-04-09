package com.example.tarekkma.avometerclient;

import com.example.tarekkma.avometerclient.events.BluetoothConnectionEvent;
import com.example.tarekkma.avometerclient.events.BluetoothStateChangedEvent;
import com.example.tarekkma.avometerclient.events.ReadingsReceivedEvent;

import org.greenrobot.eventbus.EventBus;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

/**
 * Created by tarekkma on 4/7/17.
 */

public class BluetoothUtils {
    public enum States{
        CONNECTED,CONNECTING,LISTEN,NONE
    }
    public enum Connection{
        CONNECTED,DISCONNECTED,FAILED
    }

    BluetoothSPP bt;

    public BluetoothUtils(BluetoothSPP bt) {
        this.bt = bt;
        bindListenersToEvents();
    }

    private void bindListenersToEvents(){
        bt.setBluetoothStateListener(new BluetoothSPP.BluetoothStateListener() {
            @Override
            public void onServiceStateChanged(int state) {
                BluetoothUtils.States s;
                switch (state){
                    case BluetoothState.STATE_CONNECTED:
                        s = BluetoothUtils.States.CONNECTED;
                        break;
                    case BluetoothState.STATE_CONNECTING:
                        s = BluetoothUtils.States.CONNECTING;
                        break;
                    case BluetoothState.STATE_LISTEN:
                        s = BluetoothUtils.States.LISTEN;
                        break;
                    case BluetoothState.STATE_NONE:
                        s = BluetoothUtils.States.NONE;
                        break;
                    default:return;
                }
                EventBus.getDefault().post(new BluetoothStateChangedEvent(s));
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            @Override
            public void onDeviceConnected(String name, String address) {
                EventBus.getDefault().post(new BluetoothConnectionEvent(BluetoothUtils.Connection.CONNECTED,name,address));
            }

            @Override
            public void onDeviceDisconnected() {
                EventBus.getDefault().post(new BluetoothConnectionEvent(BluetoothUtils.Connection.DISCONNECTED,null,null));
            }

            @Override
            public void onDeviceConnectionFailed() {
                EventBus.getDefault().post(new BluetoothConnectionEvent(BluetoothUtils.Connection.FAILED,null,null));
            }
        });

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                EventBus.getDefault().post(new ReadingsReceivedEvent(message));
            }
        });
    }

    public BluetoothSPP getBt(){
        return bt;
    }

    public boolean isBluetoothConnected(){
        return bt.getConnectedDeviceAddress()!=null;
    }


}
