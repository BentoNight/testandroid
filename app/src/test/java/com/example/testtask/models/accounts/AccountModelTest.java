package com.example.testtask.models.accounts;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.android.plugins.RxAndroidPlugins.reset;

public class AccountModelTest {

    AccountModel accountModel;

    @Before
    public void setSomething() {
        accountModel = new AccountModel();
    }

    @BeforeClass
    public static void before() {
        reset();
        RxJavaPlugins.reset();
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @Test
    public void checkSorting() {

        TestObserver<List<AccountData>> testObserver = accountModel.getUserDetails().test();

        List<AccountData> accounts = (List<AccountData>) testObserver.getEvents().get(0).get(0);

        int currCurrency = getValue(AccountData.MyCurrency.RUB);
        for (AccountData account : accounts) {
            int accountCurrency = getValue(account.getCurrency());
            if (accountCurrency == currCurrency) {
                continue;
            }

            if (accountCurrency == currCurrency + 1) {
                currCurrency++;
            } else {
                Assert.fail("Sorting doesn't work");
            }
        }

        testObserver.onComplete();
    }

    @Test
    public void pricePositive() {
        TestObserver<List<AccountData>> testObserver = accountModel.getUserDetails().test();

        List<AccountData> accounts = (List<AccountData>) testObserver.getEvents().get(0).get(0);

        for (AccountData account : accounts) {
            float price = account.getPrice();
            Assert.assertTrue("Negative price!", price > 0);
        }

        testObserver.onComplete();
    }

    private int getValue(AccountData.MyCurrency c) {
        return c.ordinal();
    }

    @AfterClass
    public static void after() {
        reset();
        RxJavaPlugins.reset();
    }
}