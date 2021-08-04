package com.suzhou.yipan.temperature.task;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.suzhou.yipan.temperature.util.HttpClientUtil;

@Component
public class LoadInitDataTask implements ApplicationRunner{
    private ConcurrentMap<String, Object> dataCache = new ConcurrentHashMap<>(216);
    private static final String PROVINCE_URL = "http://www.weather.com.cn/data/city3jdata/china.html";
    private static final String CITY_URL_PREX = "http://www.weather.com.cn/data/city3jdata/provshi/";
    private static final String COUNTY_URL_PREX = "http://www.weather.com.cn/data/city3jdata/station/";
    private static final String WEATHER_URL_PREX = "http://www.weather.com.cn/data/sk/";
   
	public ConcurrentMap<String, Object> getDataCache() {
		return dataCache;
	}

	public void setDataCache(ConcurrentMap<String, Object> dataCache) {
		this.dataCache = dataCache;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		initData();		
	}
	
	private void initData(){
		Object pro= requestWeatherServer(PROVINCE_URL);
		dataCache.put("province", pro);
		//todo load all the city and county data into cache
	}
	
	private Object requestWeatherServer(String url){
		String result = HttpClientUtil.postRequest(url);
		if (StringUtils.hasText(result)) {
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				return jsonObject;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return  null;
	}

}
