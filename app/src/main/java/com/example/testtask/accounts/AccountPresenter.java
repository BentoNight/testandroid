package com.example.testtask.accounts;

import com.example.testtask.models.AccountDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountPresenter implements AccountContract.Presenter {

    private final AccountContract.Repository repository;
    private final AccountContract.View view;

    public AccountPresenter(AccountContract.View view){
        this.view = view;
        this.repository = new AccountRepository();
    }

    @Override
    public void getUserDetails() {
        repository.getUserDetails()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(users -> {

                String json = users.toString();
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<AccountDetail>>(){}.getType();
                List<AccountDetail> accountDetails = gson.fromJson(json, listType);

                accountDetails.sort((o1, o2) ->
                                        Integer.compare(getAssignedValue(o1.getCurrency()), getAssignedValue(o2.getCurrency())));

                view.showUsers(accountDetails);
            });
    }

    private int getAssignedValue(AccountDetail.MyCurrency currency) {
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
