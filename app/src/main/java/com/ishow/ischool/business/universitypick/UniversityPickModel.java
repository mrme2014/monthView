package com.ishow.ischool.business.universitypick;


import com.commonlib.http.ApiFactory;
import com.ishow.ischool.bean.ApiResult;
import com.ishow.ischool.bean.university.SearchUniversityResult;
import com.ishow.ischool.bean.university.UniversityInfo;
import com.ishow.ischool.common.api.MarketApi;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wqf on 16/8/16.
 */
public class UniversityPickModel implements UniversityPickContract.Model {
    public Observable<ApiResult<ArrayList<UniversityInfo>>> getListUniversity(String cityName) {
        return ApiFactory.getInstance().getApi(MarketApi.class).getUniversity(cityName, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ApiResult<SearchUniversityResult>> searchUniversity(String universityName) {
        return ApiFactory.getInstance().getApi(MarketApi.class).searchUniversity(universityName, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}