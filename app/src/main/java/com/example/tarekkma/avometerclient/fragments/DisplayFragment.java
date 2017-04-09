package com.example.tarekkma.avometerclient.fragments;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarekkma.avometerclient.DisplayAdapter;
import com.example.tarekkma.avometerclient.data.DisplayData;
import com.example.tarekkma.avometerclient.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

import static app.akexorcist.bluetotohspp.library.BluetoothState.REQUEST_ENABLE_BT;


public class DisplayFragment extends Fragment {

    private static final String TAG = "DisplayFragment";

    BluetoothSPP bt = new BluetoothSPP(getContext());

    LinearLayout errorLayout;
    TextView errorText;
    Button errorButton;
    ContentLoadingProgressBar errorLoading;

    RecyclerView displayList;

    DisplayData vdc = DisplayData.create("vdc",0);
    DisplayData vac = DisplayData.create("vac",0);
    DisplayData r = DisplayData.create("r",0);
    DisplayData i = DisplayData.create("i",0);
    DisplayData c = DisplayData.create("c",0);

    DisplayAdapter adapter;

    public DisplayFragment() {
        // Required empty public constructor
    }


    public static DisplayFragment newInstance() {
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();
        // IF ANY ARGS NEED TO BE PASSED
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //GET ARGS
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View parentView = inflater.inflate(R.layout.fragment_diplay, container, false);

        errorLayout = (LinearLayout)parentView.findViewById(R.id.error_layout);
        errorText = (TextView) parentView.findViewById(R.id.error_text);
        errorButton = (Button) parentView.findViewById(R.id.error_button);
        errorLoading = (ContentLoadingProgressBar) parentView.findViewById(R.id.error_loading);
        displayList = (RecyclerView) parentView.findViewById(R.id.disply_list);

        displayList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new DisplayAdapter(Arrays.asList(vdc, vac, r, i, c));
        displayList.setAdapter(adapter);


        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                Log.d(TAG, "onDeviceConnected: CONNECTED name"+name+",address:"+address);
                if(getContext()==null)return;
                Toast.makeText(getContext(), "Connected to \"" + name +"\"", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() {
                Log.d(TAG, "onDeviceDisconnected: DISCONNECTED");
                if(getContext()==null)return;
                Toast.makeText(getContext(), "Disconnected", Toast.LENGTH_SHORT).show();
                refreshConnectionState();
            }

            public void onDeviceConnectionFailed() {
                Log.d(TAG, "onDeviceConnectionFailed: FAILED TO CONNECT");
                if(getContext()==null)return;
                Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                errorLoading.setVisibility(View.GONE);
                refreshConnectionState();
            }
        });

        bt.setBluetoothStateListener(new BluetoothSPP.BluetoothStateListener() {
            public void onServiceStateChanged(int state) {
                if (state == BluetoothState.STATE_CONNECTED) {
                    // Do something when successfully connected
                    Log.d(TAG, "onServiceStateChanged: STATE_CONNECTED");
                    errorLoading.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.GONE);
                } else if (state == BluetoothState.STATE_CONNECTING){
                    // Do something while connecting
                    Log.d(TAG, "onServiceStateChanged: STATE_CONNECTING");
                    errorLoading.setVisibility(View.VISIBLE);
                    errorButton.setVisibility(View.GONE);
                    errorText.setText("Trying to connect...");
                }else if(state ==BluetoothState.STATE_LISTEN){
                    // Do something when device is waiting for connection
                    Log.d(TAG, "onServiceStateChanged: STATE_LISTEN");
                }else if(state == BluetoothState.STATE_NONE){
                    // Do something when device don't have any connection
                    Log.d(TAG, "onServiceStateChanged: STATE_NONE");
                }
            }
        });

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                List<DisplayData> dataList = new ArrayList<>();
                String readings[] = message.split(",");
                for (String reading : readings) {
                    String keyValue[] = reading.split(":");
                    String key = keyValue[0];
                    double value;
                    try {
                        value = Double.parseDouble(keyValue[1]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        value = 0;
                    }
                    DisplayData item = DisplayData.create(key,value);
                    if(data!=null){
                        dataList.add(item);
                    }else{
                        Log.e(TAG, "ReadingsReceivedEvent: Message:\""+message+"\" have something wrong :\\");
                    }
                }
                adapter.updateData(dataList);
            }
        });

        return parentView;
    }

    public void sendData(View v){

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    private void setup(){

    }

    private void connectToDevice(){
        errorText.setText("Please choose the device");
        errorButton.setText("Choose");
        errorButton.setVisibility(View.VISIBLE);
        errorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DeviceList.class);
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
            }
        });
    }

    private void refreshConnectionState(){
        if (bt.getConnectedDeviceAddress() != null) return; //Device is connected no need to check
        if(!bt.isBluetoothAvailable()){
            errorText.setText("It seems like your device doesn't support bluetooth :(");
            errorButton.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }else if(!bt.isBluetoothEnabled()) {
            errorText.setText("Bluetooth is disabled !");
            errorButton.setVisibility(View.VISIBLE);
            errorButton.setText("Enable Bluetooth");
            errorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
            });
            errorButton.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.VISIBLE);
        } else {
            connectToDevice();
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK && data.getExtras()!=null)
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                bt.connect(data);
        } else if(requestCode == REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                connectToDevice();
            } else {

            }
        }
    }

    @Override
    public void onStop() {
        bt.stopService();

        super.onStop();
    }

    public void onStart() {
        super.onStart();
        refreshConnectionState();
    }


}
