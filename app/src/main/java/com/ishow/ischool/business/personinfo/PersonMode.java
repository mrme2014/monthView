package com.ishow.ischool.business.personinfo;

import com.commonlib.core.BaseModel;
import com.commonlib.http.ApiFactory;
import com.ishow.ischool.common.api.UserApi;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by MrS on 2016/8/15.
 */
public class PersonMode implements BaseModel {
    public Observable getQiNiuToken(){
       return ApiFactory.getInstance().getApi(UserApi.class).get_qiniui_token(1).subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable submitInfo(int userId,String qq,int birthday){
        return ApiFactory.getInstance().getApi(UserApi.class).edit(45,userId,birthday,qq).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
