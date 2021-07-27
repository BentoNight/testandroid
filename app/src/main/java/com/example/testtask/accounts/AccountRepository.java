package com.example.testtask.accounts;

import com.example.testtask.accounts.AccountContract;
import com.example.testtask.helpers.GenerateRandomAccounts;

import org.json.JSONObject;
import java.util.List;
import io.reactivex.Observable;

public class AccountRepository implements AccountContract.Repository {

    @Override
    public Observable<List<JSONObject>> getUserDetails() {
        return Observable.create(subscriber -> {

            List<JSONObject> users = GenerateRandomAccounts.getRandomUserDetails();

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
