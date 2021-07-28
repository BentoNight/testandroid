package com.example.testtask.accounts;

import com.example.testtask.models.accounts.AccountData;

import java.util.List;

import io.reactivex.Observable;

public interface AccountContract {

    interface View {
        void showUsers(List<AccountData> accounts);
    }

    interface Presenter {
        void getUserDetails();
    }

    interface Repository {
        Observable<List<AccountData>> getUserDetails();
    }
}
