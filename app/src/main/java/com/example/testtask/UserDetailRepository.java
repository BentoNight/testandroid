package com.example.testtask;

import org.json.JSONObject;
import java.util.List;
import io.reactivex.Observable;

public class UserDetailRepository implements UserDetailContract.Repository {

    @Override
    public Observable<List<JSONObject>> getUserDetails() {
        return Observable.create(subscriber -> {

            List<JSONObject> users = GenerateRandomDetails.getRandomUserDetails();

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                subscriber.onError(ex);
            }

            subscriber.onNext(users);
            subscriber.onComplete();
        });
    }
}
