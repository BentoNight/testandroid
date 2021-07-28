package com.example.testtask.models.accounts;

import com.example.testtask.accounts.AccountContract;
import com.example.testtask.helpers.GenerateRandomAccounts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;

public class AccountModel implements AccountContract.Repository {

    @Override
    public Observable<List<AccountData>> getUserDetails() {
        return Observable.create(subscriber -> {

            List<JSONObject> accountsJson = GenerateRandomAccounts.getRandomUserDetails();

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                subscriber.onError(ex);
            }

            String json = accountsJson.toString();
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<AccountData>>() {}.getType();
            List<AccountData> accountDetails = gson.fromJson(json, listType);

            accountDetails.sort((o1, o2) ->
                                    Integer.compare(getAssignedValue(o1.getCurrency()), getAssignedValue(o2.getCurrency())));

            subscriber.onNext(accountDetails);
            subscriber.onComplete();
        });
    }

    private int getAssignedValue(AccountData.MyCurrency currency) {
        switch (currency) {
            case RUB:
                return 0;
            case USD:
                return 1;
            case EUR:
                return 2;
            default:
            case UNKNOWN:
                return 3;
        }
    }
}
