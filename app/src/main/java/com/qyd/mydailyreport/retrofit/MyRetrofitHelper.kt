package com.qyd.mydailyreport.retrofit

import com.qyd.mydailyreport.bean.LoginBean
import com.qyd.mydailyreport.body.RegisterBody
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Ian on 2020/5/21.
 */
object MyRetrofitHelper {

    fun httpRegisterAccount(registerBody: RegisterBody, observer: MyObserver<LoginBean>) {
        MyRetrofit.getInstance()
                .retrofitServiceImpl
                .registerAccount(registerBody)
                .map(MyRetrofit.ServerResponseFunc<LoginBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

    }

}