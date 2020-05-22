package com.qyd.mydailyreport.retrofit;

import com.qyd.mydailyreport.ui.activity.person.PersonalInfoBody;
import com.qyd.mydailyreport.bean.CurrentReportDetailBean;
import com.qyd.mydailyreport.bean.LoginBean;
import com.qyd.mydailyreport.bean.ReportDetailBean;
import com.qyd.mydailyreport.body.RegisterBody;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by 林 on 2017/9/30.
 *
 * https://www.cnblogs.com/peitong/p/5981207.html
 *
 * 如果请求为post实现，那么最好传递参数时使用@Field、@FieldMap和@FormUrlEncoded。
 * 因为@Query和或QueryMap都是将参数拼接在url后面的，而@Field或@FieldMap传递的参数时放在请求体的。
 */

public interface RetrofitServiceImpl {

    @GET("users/{user}")
    Call<ResponseBody> getUserString(@Path("user") String user);

    @GET("/")
    Call<ResponseBody> helloWorld();

    @POST("/loginbypost")
    @FormUrlEncoded
    Call<ResponseBody> loginByPost(@FieldMap Map<String, Object> map);

    /**登录*/
    @POST("/loginByPost")
    @FormUrlEncoded
    Observable<HttpResult<LoginBean>> loginByPost2(@FieldMap Map<String, Object> map);

    /**注册*/
    @POST("/registerAccountByPost")
    Observable<HttpResult<LoginBean>> registerAccount(@Body RegisterBody body);
//    Observable<HttpResult<UserBean>> registerAccountByPost(@FieldMap Map<String, Object> map);

    /**修改个人资料*/
    @POST("/updatePersonalData")
    Observable<HttpResult<EmptyObject>> updatePersonalData(@Body PersonalInfoBody body);

    /**查看个人所有历史日报*/
    @POST("/getReportDetailList")
    @FormUrlEncoded
    Observable<HttpResult<ReportDetailBean>> getReportDetailList(@FieldMap Map<String, Object> map);

    /**通过日期查看所有人的日报*/
    @POST("/getReportDetailByDate")
    @FormUrlEncoded
    Observable<HttpResult<ReportDetailBean>> getReportDetailByDate(@FieldMap Map<String, Object> map);

    /**上传当天日报*/
    @POST("/uploadReportDetail")
    @FormUrlEncoded
    Observable<HttpResult<EmptyObject>> uploadReportDetail(@FieldMap Map<String, Object> map);

    /**修改当天日报*/
    @POST("/updateOldReportDetail")
    @FormUrlEncoded
    Observable<HttpResult<EmptyObject>> updateOldReportDetail(@FieldMap Map<String, Object> map);

    /**获得个人当天日报*/
    @POST("/getCurrentReport")
    @FormUrlEncoded
    Observable<HttpResult<CurrentReportDetailBean>> getCurrentReport(@FieldMap Map<String, Object> map);
}
