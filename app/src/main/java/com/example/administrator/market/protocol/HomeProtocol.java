package com.example.administrator.market.protocol;

import com.example.administrator.market.application.utils.LogUtils;
import com.example.administrator.market.bean.AppInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeProtocol extends BaseProtocol<List<AppInfo>> {
	private List<String> mPictureUrl;

	@Override
	protected String getKey() {
		return "home";
	}

	public List<String> getPictureUrl() {
		return mPictureUrl;
	}
//解析服务器传来的Json数据
	@Override
	protected List<AppInfo> parseFromJson(String json) {
		try {
			//解析首页轮播图的图片
			JSONObject jsonObject = new JSONObject(json);
			mPictureUrl = new ArrayList<String>();
			JSONArray array = jsonObject.optJSONArray("picture");//解析到图片数组
			if(array != null){  //将解析到的图片地址数组放到一个List集合中去
				for (int i = 0; i < array.length(); i++) {
					mPictureUrl.add(array.getString(i));
				}
			}
			List<AppInfo> list = new ArrayList<AppInfo>();  //List集合用于存放list中的数组元素
			array = jsonObject.getJSONArray("list");  //得到list数组中的所有对象
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);  //得到list数组中的第i个元素
				AppInfo info = new AppInfo();			//将第i个元素赋值给bean
				info.setId(obj.getLong("id"));
				info.setName(obj.getString("name"));
				info.setPackageName(obj.getString("packageName"));
				info.setIconUrl(obj.getString("iconUrl"));
				info.setStars(Float.valueOf(obj.getString("stars")));
				info.setSize(obj.getLong("size"));
				info.setDownloadUrl(obj.getString("downloadUrl"));
				info.setDes(obj.getString("des"));
				list.add(info);       //将第i个数组元素添加到List集合中
			}
			return list;
		} catch (Exception e) {
			LogUtils.e(e);
			return null;
		}
	}

}
