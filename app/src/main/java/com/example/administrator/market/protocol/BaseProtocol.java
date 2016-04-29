package com.example.administrator.market.protocol;

import android.content.Context;
import android.os.SystemClock;

import com.example.administrator.market.application.utils.FileUtils;
import com.example.administrator.market.application.utils.IOUtils;
import com.example.administrator.market.application.utils.LogUtils;
import com.example.administrator.market.application.utils.StringUtils;
import com.example.administrator.market.http.HttpHelper;
import com.example.administrator.market.http.NoHttpHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by mwqi on 2014/6/7.
 * 此类用来解析从服务器获取的Json数据
 * 因为每个页面的数据内容Bean不同，所以需要子类去继承本类进行内容的解析
 */
public abstract class BaseProtocol<Data> {
    public static final String cachePath = "";
    public static final long CACHE_SAVE_TIME = 1000 * 60;

    /**
     * 加载协议
     */
    public Data loadDataWithCache(int index) {
        SystemClock.sleep(1000);// 休息1秒，防止加载过快，看不到界面变化效果
        String json = null;
        // 1.从本地缓存读取数据，查看缓存时间
        json = loadFromLocal(index);
        // 2.如果缓存时间过期，从网络加载
        if (StringUtils.isEmpty(json)) {
            json = loadFromNet(index);
            if (json == null) {
                // 网络出错
                return null;
            } else {
                // 3.把数据保存到本地保存到本地
                saveToLocal(json, index);
            }
        }
        return parseFromJson(json);   //解析Json数据
    }


    public Data loadDataNoCache(Context context,String index) {
        SystemClock.sleep(1000);// 休息1秒，防止加载过快，看不到界面变化效果
        String json = loadFromNet(context,index);
        if (json == null) {
            // 网络出错
            return null;
        } else {
            return parseFromJson(json);
        }
    }

    /**
     * 从本地加载协议
     */
    protected String loadFromLocal(int index) {
        String path = FileUtils.getCacheDir();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path + getKey() + "_" + index + getParames()));
            String line = reader.readLine();// 第一行是时间
            Long time = Long.valueOf(line);
            if (time > System.currentTimeMillis()) {//如果时间未过期
                StringBuilder sb = new StringBuilder();
                String result;
                while ((result = reader.readLine()) != null) {
                    sb.append(result);
                }
                return sb.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(reader);
        }
        return null;
    }

    /**
     * 从网络加载协议
     */
    protected String loadFromNet(int index) {
        String result = null;
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey() + "?index=" + index + getParames());
        if (httpResult != null) {
            result = httpResult.getString();
            httpResult.close();
        }
        return result;
    }

    protected String loadFromNet(Context context,String s) {
        return NoHttpHelper.getInstance().get(context,s);
    }


    /**
     * 保存到本地，缓存过期时间是CACHE_SAVE_TIME
     */
    protected void saveToLocal(String str, int index) {
        String path = FileUtils.getCacheDir();
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path + getKey() + "_" + index + getParames()));
            long time = System.currentTimeMillis() + CACHE_SAVE_TIME;//先计算出过期时间，写入第一行
            writer.write(time + "\r\n");
            writer.write(str.toCharArray());
            writer.flush();
        } catch (Exception e) {
            LogUtils.e(e);
        } finally {
            IOUtils.close(writer);
        }
    }

    /**
     * 需要增加的额外参数
     */
    protected String getParames() {
        return "";
    }

    /**
     * 该协议的访问地址
     */
    protected abstract String getKey();

    /**
     * 从json中解析，因为每个页面需要解析的数据不一样，所以放到子类去实现
     */
    protected abstract Data parseFromJson(String json);
}
