package com.example.animalswiki.entities;

import com.google.gson.annotations.SerializedName;

public class Weight {

    @SerializedName("metric")
    private String metric;

    public Weight( String metric) {

        this.metric = metric;
    }



    public String getMetric() {
        return metric;
    }

    @Override
    public String toString() {
        return "Weight{" +
                ", metric='" + metric + '\'' +
                '}';
    }
}
