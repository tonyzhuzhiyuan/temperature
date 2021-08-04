package com.suzhou.yipan.temperature.filter;

import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import com.suzhou.yipan.temperature.util.FluxUtil;
import com.suzhou.yipan.temperature.util.HttpClientUtil;

@Component
public class TempRequestFilter implements GlobalFilter, Ordered {
	private static final String TEMP_URL_PREX = "http://www.weather.com.cn/data/sk/";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange,
			GatewayFilterChain chain) {
		if (!ServerWebExchangeUtils.isAlreadyRouted(exchange)) {
			ServerWebExchangeUtils.setAlreadyRouted(exchange);
			ServerHttpRequest request = exchange.getRequest();
			String action = getAction(request);
			String province = action.substring(0, 4);
			String city = action.substring(5, 6);
			String county = action.substring(7, 8);
			//check location, if location is invalid,return;
			if(checkLocation(province,city,county)){
				return FluxUtil.writeFlux(exchange, "location is invalid", chain);
			}else{
				Optional<Integer> data = getTemperature(province, city, county);
				return FluxUtil.writeFlux(exchange, data.get().toString(), chain);
			}
		
		} else {
			return chain.filter(exchange);
		}
	}

	public Optional<Integer> getTemperature(String province, String city,
			String county) {
		if (StringUtils.hasText(province) && StringUtils.hasText(city)
				&& StringUtils.hasText(county)) {
			String url = TEMP_URL_PREX + province + city + county + ".html";
			String result = HttpClientUtil.postRequest(url);
			if (StringUtils.hasText(result)) {
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(result);
					String temp = (String) jsonObject.get("temp");
					return Optional.ofNullable(Integer.getInteger(temp));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String getAction(ServerHttpRequest request) {
		String reqStr = request.getPath().pathWithinApplication().value();
		int endInx = reqStr.lastIndexOf(".");
		int startInx = reqStr.lastIndexOf("/");
		if (endInx != -1 && startInx != -1) {
			reqStr = reqStr.substring(startInx, endInx);
		}
		return reqStr;
	}
	
	private boolean checkLocation(String p,String city,String county  ){
		return true;
	}
	
	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

}
