package com.example.testtask.accounts;

import com.example.testtask.models.AccountDetail;
import org.json.JSONObject;
import java.util.List;
import io.reactivex.Observable;

public interface AccountContract {

    interface View{
        void showUsers(List<AccountDetail> accounts);
    }

    interface Presenter {
        void getUserDetails();
    }

    interface Repository{
        Observable<List<JSONObject>> getUserDetails();
    }
}
