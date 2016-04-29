package com.example.administrator.market.http;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.example.administrator.market.constants.UrlConstants;
import com.example.administrator.market.http.nohttp.CallServer;
import com.example.administrator.market.http.nohttp.HttpListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.Response;

/**
 * Created by Luo Shuiquan on 4/21/2016.
 */
public class NoHttpHelper {
    private NoHttpHelper() {
    }

    private static class NoHttpHelperHolder {
        private static final NoHttpHelper INSTANCE = new NoHttpHelper();
    }

    public static final NoHttpHelper getInstance() {
        return NoHttpHelperHolder.INSTANCE;
    }

    public String get(Context context, String s) {
        Request<JSONObject> request = null;
        String result = null;
        switch (s) {
            case UrlConstants.URL_GET_HOME_DATA:
                NoHttp.createJsonObjectRequest(UrlConstants.URL_GET_HOME_DATA);
                break;

        }

        CallServer.getRequestInstance().add(context, 0, request, objectListener, true, true);
        return result;
    }

    public static String post(String s) {
        return null;
    }


    private HttpListener<JSONObject> objectListener = new HttpListener<JSONObject>() {
        @Override
        public void onSucceed(int what, Response<JSONObject> response) {
            JSONObject jsonObject = response.get();
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        }
    };
}
