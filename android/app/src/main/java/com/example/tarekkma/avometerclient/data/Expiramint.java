package com.example.tarekkma.avometerclient.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tarekkma on 4/29/17.
 */

public class Expiramint {
    public String name;
    @SerializedName("file_name")
    public String file_path;
    public String index;

    public Expiramint(String name, String file_path, String index) {
        this.name = name;
        this.file_path = file_path;
        this.index = index;
    }
}
