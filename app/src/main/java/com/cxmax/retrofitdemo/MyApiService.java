package com.cxmax.retrofitdemo;

import com.cxmax.retrofitdemo.bean.IpResult;
import com.cxmax.retrofitdemo.bean.SouguBean;
import com.cxmax.retrofitdemo.retrofit.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cxmax on 2017/2/13.
 */

public interface MyApiService {

    @GET("service/getIpInfo.php")
    Observable<BaseResponse<IpResult>> getData(@Query("ip") String ip);

    @GET("app.php?m=souguapp&c=appusers&a=network")
    Observable<SouguBean> getSougu();
}
