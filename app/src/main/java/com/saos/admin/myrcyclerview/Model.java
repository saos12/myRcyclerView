package com.saos.admin.myrcyclerview;

import java.io.Serializable;

/**
 * Created by Admin on 20/08/2018.
 */

public class Model implements Serializable {


    private String data;
    private String details;

    public Model(String data, String details) {
        this.data = data;
        this.details = details;
    }

    public Model(String data) {

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
