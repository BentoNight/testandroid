package com.example.testtask;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;

public interface UserDetailContract {

    interface View{
        void showUsers(List<UserData> userData);
    }

    interface Presenter {
        void getUserDetails();
    }

    interface Repository{
        Observable<List<JSONObject>> getUserDetails();
    }
}
