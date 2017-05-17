package com.example.tarekkma.avometerclient.data;

import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by tarekkma on 4/6/17.
 */

public class DisplayData {

    private static final String TAG = "DisplayData";

    private String key;
    private double value;
    private String additionalInfo;
    private String name;
    private MeasureUnit unit;


    @Nullable
    public static DisplayData create(String key, double value){
        DisplayData data = new DisplayData();
        MeasureUnit unit;
        switch (key){
            case "vdc":
                unit = new MeasureUnit("AC Volt","AC V","V",MeasureUnit.NORMAL);
                break;
            case "vac":
                unit = new MeasureUnit("DC Volt","DC V","V",MeasureUnit.NORMAL);
                break;
            case "r":{
                Multiplier m = MeasureUnit.NORMAL;
                if(value>1000){
                    value/=1000;
                    m = MeasureUnit.KILO;
                }
                unit = new MeasureUnit("Ohm","R","Ω",m);
                break;}
            case "i":{
                Multiplier m = MeasureUnit.NORMAL;
                if(value<1){
                    value*=1000;
                    m = MeasureUnit.MILLI;
                }
                unit = new MeasureUnit("Ampere","I","A",m);
                break;}
            case "c":
                unit = new MeasureUnit("Capacitance","C","F",MeasureUnit.MICRO);
                break;
            default:
                Log.e(TAG, "DisplayData: Key :'"+key+"' doesn't match any defined keys.");
                return null;
        }
        data.setKey(key);
        data.setValue(value);
        data.setUnit(unit);
        return data;
    }

    public MeasureUnit getUnit() {
        return unit;
    }

    public void setUnit(MeasureUnit unit) {
        this.unit = unit;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
