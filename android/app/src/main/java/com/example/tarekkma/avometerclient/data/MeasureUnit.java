package com.example.tarekkma.avometerclient.data;

/**
 * Created by tarekkma on 4/6/17.
 */

public class MeasureUnit {

    public static final Multiplier MICRO = new Multiplier("micro","μ",6);
    public static final Multiplier MILLI = new Multiplier("milli","m",3);
    public static final Multiplier NORMAL = new Multiplier("","",0);
    public static final Multiplier KILO = new Multiplier("kilo","K",-3);

    public String name;
    public String symbol;
    public String unit;
    public Multiplier multiplier;

    public MeasureUnit(String name, String abbreviation, String symbol, Multiplier defaultMultiplier) {
        this.name = name;
        this.symbol = abbreviation;
        this.unit = symbol;
        this.multiplier = defaultMultiplier;
    }


}
