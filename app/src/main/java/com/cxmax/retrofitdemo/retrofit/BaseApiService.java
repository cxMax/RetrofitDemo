package com.cxmax.retrofitdemo.retrofit;

import com.cxmax.retrofitdemo.bean.IpResult;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by cxmax on 2017/2/14.
 */

public interface BaseApiService {

    public static final String BASE_URL =  "http://ip.taobao.com/";

    @GET("service/getIpInfo.php")
    Observable<BaseResponse<IpResult>> getData(@Query("ip") String ip);

    @GET("{url}")
    Observable<BaseResponse<IpResult>> executeGet(
            @Path("url")String url,
            @QueryMap Map<String, String> maps);

    @POST("{url}")
    Observable<ResponseBody> executePost(
            @Path("url")String url,
            @QueryMap Map<String, String> maps);

    @POST("{url}")
    Observable<ResponseBody> json(@Path("url") String url,
                                  @Body RequestBody jsonStr);

    @Multipart
    @POST("{url}")
    Observable<ResponseBody> upLoadFile(
            @Path("url") String url,
            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    @POST("{url}")
    Call<ResponseBody> uploadFiles(
            @Path("url") String url,
            @Path("headers") Map<String, String> headers,
            @Part("filename") String description,
            @PartMap() Map<String, RequestBody> maps);

    @Streaming
    @GET
    rx.Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
