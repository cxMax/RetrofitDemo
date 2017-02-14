package com.cxmax.retrofitdemo.retrofit;

/**
 * Created by cxmax on 2017/2/15.
 */

public abstract class CallBack {
    public void onStart(){}

    public void onCompleted(){}

    abstract public void onError(Throwable e);

    public void onProgress(long fileSizeDownloaded){}

    abstract public void onSucess(String path, String name, long fileSize);
}
