package com.cxmax.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cxmax.retrofitdemo.bean.IpResult;
import com.cxmax.retrofitdemo.bean.SouguBean;
import com.cxmax.retrofitdemo.retrofit.BaseResponse;
import com.cxmax.retrofitdemo.retrofit.BaseSubscriber;
import com.cxmax.retrofitdemo.retrofit.CallBack;
import com.cxmax.retrofitdemo.retrofit.ExceptionHandle;
import com.cxmax.retrofitdemo.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View btn, btn_get, btn_post, btn_json, btn_download, btn_upload, btn_myApi, btn_changeHostApi;

    String url1 = "http://img0.imgtn.bdimg.com/it/u=205441424,1768829584&fm=21&gp=0.jpg";
    String url2 = "http://wap.dl.pinyin.sogou.com/wapdl/hole/201607/05/SogouInput_android_v8.3_sweb.apk?frm=new_pcjs_index";
    String url3 = "http://apk.hiapk.com/web/api.do?qt=8051&id=723";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn = findViewById(R.id.bt_getdata);
        btn_get = findViewById(R.id.bt_get);
        btn_post = findViewById(R.id.bt_post);
        btn_json = findViewById(R.id.bt_json);
        btn_download = findViewById(R.id.bt_download);
        btn_upload = findViewById(R.id.bt_upload);
        btn_myApi = findViewById(R.id.bt_my_api);
        btn_changeHostApi = findViewById(R.id.bt_changeHostApi);
        btn.setOnClickListener(this);
        btn_get.setOnClickListener(this);
        btn_post.setOnClickListener(this);
        btn_json.setOnClickListener(this);
        btn_download.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
        btn_myApi.setOnClickListener(this);
        btn_changeHostApi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_getdata:
                demoForGetData();
                break;
            case R.id.bt_get:
                demoForGetMethod();
                break;
            case R.id.bt_post:
                demoForPostMethod();
                break;
            case R.id.bt_json:
                demoForGetJson();
                break;
            case R.id.bt_upload:
                demoForUpload();
                break;
            case R.id.bt_download:
                demoForDownload();
                break;
            case R.id.bt_my_api:
                demoForApi();
                break;
            case R.id.bt_changeHostApi:
                demoForChangeHostApi();
                break;
        }
    }

    private void demoForChangeHostApi() {
        //create  you APiService
        MyApiService service = RetrofitClient.getInstance(MainActivity.this, "http://lbs.sougu.net.cn/").create(MyApiService.class);

        // execute and add observable to RxJava
        RetrofitClient.getInstance(MainActivity.this, "http://lbs.sougu.net.cn/").execute(
                service.getSougu(), new BaseSubscriber<SouguBean>(MainActivity.this) {

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        Log.e("Lyk", e.getMessage());
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onNext(SouguBean souguBean) {

                        Toast.makeText(MainActivity.this, souguBean.toString(), Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void demoForApi() {
        MyApiService service = RetrofitClient.getInstance(MainActivity.this, "http://ip.taobao.com/").create(MyApiService.class);

        // execute and add observable
        RetrofitClient.getInstance(MainActivity.this).execute(

                service.getData("21.22.11.33"), new BaseSubscriber<BaseResponse<IpResult>>(MainActivity.this) {

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        Log.e("Lyk", e.getMessage());
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onNext(BaseResponse<IpResult> responseBody) {

                        if (responseBody.isOk()) {
                            IpResult ip = responseBody.getData();
                            Toast.makeText(MainActivity.this, ip.toString(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    private void demoForDownload() {
        RetrofitClient.getInstance(MainActivity.this).createBaseApi().download(url3, new CallBack() {

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onProgress(long fileSizeDownloaded) {
                        super.onProgress(fileSizeDownloaded);
                    }

                    @Override
                    public void onSucess(String path, String name, long fileSize) {

                    }
                }
        );
    }

    private void demoForUpload() {

    }

    private void demoForGetJson() {
        Map<String, String> maps = new HashMap<String, String>();

        maps.put("ip", "21.22.11.33");
        //"http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33";

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(maps));

        RetrofitClient.getInstance(MainActivity.this)
                .createBaseApi()
                .json("service/getIpInfo.php"
                        , body, new BaseSubscriber<IpResult>(MainActivity.this) {


                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable e) {

                            }

                            @Override
                            public void onNext(IpResult responseBody) {

                            }
                        });
    }

    private void demoForPostMethod() {
        Map<String, String> maps = new HashMap<String, String>();

        maps.put("ip", "21.22.11.33");
        //"http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33";
        RetrofitClient
                .getInstance(MainActivity.this)
                .createBaseApi()
                .post("service/getIpInfo.php"
                        , maps, new BaseSubscriber<ResponseBody>(MainActivity.this) {

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable e) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                            }
                        });
    }

    private void demoForGetMethod() {
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("ip", "21.22.11.33");
        RetrofitClient.getInstance(MainActivity.this)
                .createBaseApi()
                .get("service/getIpInfo.php"
                        , maps, new BaseSubscriber<IpResult>(MainActivity.this) {


                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable e) {

                            }

                            @Override
                            public void onNext(IpResult responseBody) {

                            }
                        });
    }

    private void demoForGetData() {
        RetrofitClient.getInstance(this)
                .createBaseApi()
                .getData(new BaseSubscriber<IpResult>(MainActivity.this) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(IpResult ipResult) {

                    }
                }, "21.22.11.33");
    }
}
