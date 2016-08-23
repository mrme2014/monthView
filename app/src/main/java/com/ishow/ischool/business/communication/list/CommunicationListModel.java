package com.ishow.ischool.business.communication.list;

import com.commonlib.http.ApiFactory;
import com.ishow.ischool.bean.ApiResult;
import com.ishow.ischool.bean.market.CommunicationList;
import com.ishow.ischool.common.api.CommunicationApi;

import java.util.HashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by abel on 16/8/15.
 */
public class CommunicationListModel implements CommunicationListContract.Model {

    public Observable<ApiResult<CommunicationList>> listCommunications(HashMap<String, String> params) {
        return ApiFactory.getInstance().getApi(CommunicationApi.class)
                .listCommnunication(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}