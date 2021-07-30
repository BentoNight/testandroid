package com.example.testtask.accounts;

import com.example.testtask.models.accounts.AccountModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AccountPresenter implements AccountContract.Presenter {

    private final AccountContract.Repository repository;
    private final AccountContract.View view;

    public AccountPresenter(AccountContract.View view) {
        this.view = view;
        this.repository = new AccountModel();
    }

    @Override
    public void getUserDetails() {
        repository.getUserDetails()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view::showUsers);

    }
}
