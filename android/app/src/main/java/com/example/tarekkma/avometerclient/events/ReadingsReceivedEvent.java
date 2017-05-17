package com.example.tarekkma.avometerclient.events;

import android.util.Log;

import com.example.tarekkma.avometerclient.data.DisplayData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarekkma on 4/7/17.
 */

public class ReadingsReceivedEvent {

    private static final String TAG = "ReadingsReceivedEvent";
    public List<DisplayData> dataList = new ArrayList<>();

    public ReadingsReceivedEvent(String s) {
        String readings[] = s.split(",");
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
            DisplayData data = DisplayData.create(key,value);
            if(data!=null){
                dataList.add(data);
            }else{
                Log.e(TAG, "ReadingsReceivedEvent: Message:\""+s+"\" have something wrong :\\");
            }
        }
    }

}
