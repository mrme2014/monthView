package com.ishow.ischool.business.student.pick;

import com.commonlib.core.BaseModel;
import com.commonlib.core.BasePresenter;
import com.commonlib.core.BaseView;
import com.ishow.ischool.bean.ApiResult;
import com.ishow.ischool.bean.student.StudentStatisticsList;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by abel on 16/8/17.
 */
public interface PickStudentContract {
    interface Model extends BaseModel {
        Observable<ApiResult<StudentStatisticsList>> getStudentStatisticsList(HashMap<String, String> params);
    }

    interface View extends BaseView {
        void getListSuccess(StudentStatisticsList studentStatisticsList);

        void getListFail(String msg);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getStudentStatisticsList(HashMap<String, String> params);
    }
}