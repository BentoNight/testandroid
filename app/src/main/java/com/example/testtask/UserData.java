package com.example.testtask;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserData {
    @SerializedName("id")
    private final Integer id;
    @SerializedName("firstName")
    private final String firstName;
    @SerializedName("lastName")
    private final String lastName;
    @SerializedName("details")
    private List<DetailData> details;

    public UserData(Integer id, String firstName, String lastName, List<DetailData> details) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.details = details;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public List<DetailData> getDetails(){
        return this.details;
    }

    public void setDetails(List<DetailData> details){
        this.details = details;
    }

    public String getName(){
        return this.firstName + " " + this.lastName;
    }

    public Integer getId() {
        return this.id;
    }
}
