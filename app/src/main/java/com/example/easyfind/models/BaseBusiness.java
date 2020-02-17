package com.example.easyfind.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseBusiness {

    @SerializedName("businesses")
    @Expose private List<Business> businesses = null;
    @SerializedName("total")
    @Expose private Integer total;


    public List<Business> getBusinesses() {
        return businesses;
    }

    public Integer getTotal() {
        return total;
    }
}
