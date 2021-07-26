package com.example.testtask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserDetailPresenter implements UserDetailContract.Presenter {

    private UserDetailContract.Repository repository;
    private UserDetailContract.View view;

    public UserDetailPresenter(UserDetailContract.View view){
        this.view = view;
        this.repository = new UserDetailRepository();
    }

    @Override
    public void getUserDetails() {
        repository.getUserDetails()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(users -> {

                String json = users.toString();
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<UserData>>(){}.getType();
                List<UserData> userdata = gson.fromJson(json, listType);

                for (UserData item : userdata){
                    item.getDetails().sort(new Comparator<DetailData>() {

                        @Override
                        public int compare(DetailData o1, DetailData o2) {
                            return Integer.compare(getAssignedValue(o1.getCurrency()), getAssignedValue(o2.getCurrency()));
                        }
                    });
                }

                view.showUsers(userdata);
            });
    }

    private int getAssignedValue(DetailData.MyCurrency currency) {
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
