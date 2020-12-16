package com.qyd.mydailyreport.retrofit;

import android.text.TextUtils;

import com.linqinen.library.utils.LogT;
import com.qyd.mydailyreport.BuildConfig;
import com.qyd.mydailyreport.utils.MySharedPreferences;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.internal.Util.UTF_8;

/**
 * Created by Ian on 2017/9/30.
 */

public class MyRetrofit {

            private final String MAIN_ENGINE = "http://47.100.229.22:8090/";
//    private static final String MAIN_ENGINE = "http://10.0.2.2:8090/";//本地地址，无法直接使用localhost，连接不上

    private static final int DEFAULT_TIMEOUT = 10;//默认超时时间
    private RetrofitServiceImpl mRetrofitServiceImpl;//Retrofit 接口

    private OkHttpClient.Builder mOkHttpClient;
    private static String token;

    public RetrofitServiceImpl getRetrofitServiceImpl() {
        return mRetrofitServiceImpl;
    }

    //创建单例
    private static class SingletonHolder {
        private static final MyRetrofit INSTANCE = new MyRetrofit();
    }

    //获取单例
    public static MyRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 构造私有方法
     */
    private MyRetrofit() {
        init();
    }

    public static void setToken(String token) {
        MyRetrofit.token = token;

    }

    public static String getToken() {
        if (TextUtils.isEmpty(token)) {
            token = MySharedPreferences.getInstance().getToken();
        }
//        LogT.i("token:" + token);
        return token;
    }

    /**
     * 初始化Retrofit
     */
    private void init() {
        mOkHttpClient = new OkHttpClient.Builder();
        /*设置超时时间，源代码好像默认时间是10秒*/
//        mOkHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        addInterceptor();
        mRetrofitServiceImpl = new Retrofit.Builder()
                .client(mOkHttpClient.build())//设置OkHttpClient
                .baseUrl(MAIN_ENGINE)//设置服务器地址
                .addConverterFactory(GsonConverterFactory.create())//添加Gson解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加RxJava支持
                .build()
                .create(RetrofitServiceImpl.class);//创建Retrofit接口

    }

    /**
     * https://blog.csdn.net/zhuhai__yizhi/article/details/52956719
     */
    private void addInterceptor() {

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            设置 Debug Log 模式
            mOkHttpClient.addInterceptor(loggingInterceptor);
        }

//        LogT.i("增加token验证:" + token);

//        if (!TextUtils.isEmpty(getToken())) {
//        LogT.i("增加拦截器mOkHttpClient:" + mOkHttpClient);
        mOkHttpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                RequestBody requestBody = original.body();
                if (requestBody != null) {
//                    LogT.i("请求参数:" + requestBody.toString());
                    printParams(requestBody);
                }
//                LogT.i("getToken():" + getToken());
                Request request = original.newBuilder()
                        .header("Authorization", getToken())
//                        .addHeader("Authorization", getTokenPhone())
//                        .header("User-Agent", "Your-App-Name")
                        .method(original.method(), original.body())
                        .build();

//                LogT.i("header==" + request.headers());
                return chain.proceed(request);
            }
        });
//        }
    }

    /**
     * https://blog.csdn.net/ZhangXuxiaoqingnian/article/details/84030832
     */
    private void printParams(RequestBody body) {
        Buffer buffer = new Buffer();
        try {
            body.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF_8);
            }
            if (charset == null) {
                return;
            }
            String params = buffer.readString(charset);
            LogT.i("请求参数：" + params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这个是最原始的Retrofit例子，仅作为参考使用
     * http://blog.csdn.net/TOYOTA11/article/details/53454925
     */
    private void login() {

        Map<String, Object> map = new HashMap<>();
        map.put("account", "account");
        map.put("password", "password");


        Call<ResponseBody> call = mRetrofitServiceImpl.loginByPost(map);
        /*采用异步访问网络*/
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LogT.i("result:" + response.body().toString());

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                LogT.i("call.request().url():" + call.request().url() + ",t:" + t);
            }
        });
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的resultData部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是resultData部分的数据类型
     *            <p>
     *            Func1的<I,O>I,O模版分别为输入和输出值的类型
     */
    public static class ServerResponseFunc<T> implements Function<HttpResult<T>, T> {

        @Override
        public T apply(HttpResult<T> httpResult) {
            //对返回码进行判断，如果不是0，则证明服务器端返回错误信息了，便根据跟服务器约定好的错误码去解析异常

            if (httpResult.getCode() != 0) {
                //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
                LogT.e("错误信息code:" + httpResult.getCode() + "," + httpResult.getMessage());
//                Toast.makeText(MyApplication.getMyApplicaiton(),httpResult.getMessage(),Toast.LENGTH_SHORT).show();
                throw new ServerException(
                        httpResult.getCode(), httpResult.getMessage());
            }
            //服务器请求数据成功，返回里面的数据实体
            return httpResult.getData();
        }
    }

    public static StringBuilder getParameters(Map<String, Object> map) {
        StringBuilder info = new StringBuilder();
        info.append(", 参数：");
        for (Map.Entry entry : map.entrySet()) {
            info.append((String) entry.getKey()).append(" = ").append(entry.getValue()).append(", ");
        }
//        LogT.i("info:" + info);
        return info;
    }
}
