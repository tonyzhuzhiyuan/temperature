package com.suzhou.yipan.temperature.util;

import java.io.IOException;
import java.io.InterruptedIOException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	
	public static String postRequest(String url) {
		HttpPost httpPost = new HttpPost(url);
		HttpResponse resp = null;
		CloseableHttpClient client = getHttpClient();
		String result = null;
		try {
			resp = client.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(resp.getEntity(), "UTF-8").trim();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.abort();
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static CloseableHttpClient getHttpClient() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(20);
		cm.setDefaultMaxPerRoute(20);
		CloseableHttpClient httpClient = HttpClients.custom()
				.setRetryHandler(makeRetryHandler()).setConnectionManager(cm)
				.build();
		return httpClient;
	}

	private static HttpRequestRetryHandler makeRetryHandler() {
		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {
				if (executionCount >= 3) {
					return false;
				}
				if (exception instanceof InterruptedIOException
						|| exception instanceof ConnectTimeoutException) {
					return true;
				}
				HttpClientContext clientContext = HttpClientContext
						.adapt(context);
				HttpRequest request = clientContext.getRequest();
				boolean flag = !(request instanceof HttpEntityEnclosingRequest);
				if (flag) {
					return true;
				}
				return false;
			}
		};
		return retryHandler;
	}


}
