/**
 * file name : CrawlerConfig.java
 * created at : 3:24:46 PM Jul 26, 2015
 * created by 970655147
 */

package com.hx.crawlerTools_crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

// CrawlerConfig
public class CrawlerConfig {
	
	// 请求头信息, cookies信息, postData信息
	// 超时配置
	private List<Header> headers;
	private Map<String, String> cookies;
	private List<NameValuePair> data;
	private int timeout;
	
	// 常量
	private static int DEFAULT_TIMEOUT = 10 * 1000;
	private static Map<String, String> DEFAULT_HEADERS = new HashMap<>();
	static {
		DEFAULT_HEADERS.put("Content-Type", "text/html;charset=utf-8");
		DEFAULT_HEADERS.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
//		DEFAULT_HEADERS.put("Accept-Encoding", "gzip, deflate, sdch");
//		DEFAULT_HEADERS.put("Accept-Language", "zh-CN,zh;q=0.8");
//		DEFAULT_HEADERS.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	}
	
	// 初始化 添加默认的请求头
	public CrawlerConfig() {
		headers = new ArrayList<>();
		cookies = new HashMap<>();
		data = new ArrayList<>();
		timeout = DEFAULT_TIMEOUT;
		
		for(Entry<String, String> header : DEFAULT_HEADERS.entrySet()) {
			headers.add(new BasicHeader(header.getKey(), header.getValue()) );
		}
	}
	
	// getter & setter
	public List<Header> getHeaders() {
		return headers;
	}
	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}
	public void setHeaders(Map<String, String> headers) {
		List<Header> headersTmp = new ArrayList<>(headers.size() );
		for(Map.Entry<String, String> entry : headers.entrySet() ) {
			headersTmp.add(new BasicHeader(entry.getKey(), entry.getValue()) );
		}
		this.headers = headersTmp;
	}
	public void setData(Map<String, String> data) {
		List<NameValuePair> dataTmp = new ArrayList<>(data.size() );
		for(Map.Entry<String, String> entry : data.entrySet() ) {
			dataTmp.add(new BasicNameValuePair(entry.getKey(), entry.getValue()) );
		}
		this.data = dataTmp;
	}
	public Map<String, String> getCookies() {
		return cookies;
	}
	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}
	public List<NameValuePair> getData() {
		return data;
	}
	public void setData(List<NameValuePair> data) {
		this.data = data;
	}
	public void addHeader(String key, String value) {
		int idx = indexOfHeader(key);
		if(idx >= 0) {
			headers.remove(idx);
		}
		headers.add(new BasicHeader(key, value));
	}
	public void addCookie(String key, String value) {
		cookies.put(key, value);
	}
	public void addData(String key, String value) {
		int idx = indexOfData(key);
		if(idx >= 0) {
			data.remove(idx);
		}
		data.add(new BasicNameValuePair(key, value) );
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	// key 在headers, data中的索引
	private int indexOfHeader(String key) {
		for(int i=0; i<headers.size(); i++) {
			if(headers.get(i).getName().equals(key) ) {
				return i;
			}
		}
		
		return -1;
	}
	private int indexOfData(String key) {
		for(int i=0; i<data.size(); i++) {
			if(data.get(i).getName().equals(key) ) {
				return i;
			}
		}
		
		return -1;
	}
	
}
