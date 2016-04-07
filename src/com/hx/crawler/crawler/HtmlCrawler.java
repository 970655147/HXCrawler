/**
 * file name : Crawler.java
 * created at : 3:24:26 PM Jul 26, 2015
 * created by 970655147
 */

package com.hx.crawler.crawler;

import java.io.IOException;
import java.util.Iterator;

import org.apache.http.Header;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;

import com.hx.crawler.crawler.interf.Crawler;
import com.hx.crawler.util.Tools;

// HtmlCrawler
public class HtmlCrawler extends Crawler {

	// getInstance 获取到的唯一实例
	private static HtmlCrawler instance;
	
	// 创建/ 获取一个HtmlCrawler
	public static HtmlCrawler newInstance() {
		return new HtmlCrawler();
	}
	public static HtmlCrawler getInstance() {
		if(instance == null) {
			synchronized (HtmlCrawler.class) {
				if(instance == null) {
					instance = newInstance();
				}
			}
		}
		
		return instance;
	}
	
	// getPage
	public Page getPage(String url) throws IOException {
		return getPage(url, new CrawlerConfig());
	}
	public Page getPage(String url, CrawlerConfig config) throws IOException {
		Request req = Request.Get(url);
		req.connectTimeout(config.getTimeout() );
		setHeadersAndCookies(req, config);
		
		Response resp = req.execute();
		return new Page(resp);
	}
	
	// postPage
	public Page postPage(String url) throws IOException {
		return postPage(url, new CrawlerConfig());
	}
	public Page postPage(String url, CrawlerConfig config) throws IOException {
		Request req = Request.Post(url);
		config(req, config);
		
		Response resp = req.execute();
		return new Page(resp);
	}
	public Page postPage(String url, CrawlerConfig config, String bodyData, ContentType contentType) throws IOException {
		Request req = Request.Post(url);
		config(req, config);
		req.bodyString(bodyData, contentType);
		
		Response resp = req.execute();
		return new Page(resp);
	}
	
	// 使用crawlerConfig 配置request
	private static void config(Request req, CrawlerConfig config) {
		setHeadersAndCookies(req, config);
		req.bodyForm(config.getData() );
	}
	
	// 为request设置请求头 & cookies
	private static void setHeadersAndCookies(Request req, CrawlerConfig config) {
		Iterator<Header> it = config.getHeaders().iterator();
		boolean existCookiesInHeader = false;
		
		while(it.hasNext() ) {
			Header header = it.next();
			if(Tools.equalsIgnoreCase(header.getName(), Tools.COOKIE_STR) ) {
				header = new BasicHeader(header.getName(), Tools.removeIfEndsWith(header.getValue(), ";") + ";" + Tools.getCookieStr(config.getCookies()) );
				existCookiesInHeader = true;
			}
			
			req.addHeader(header );
		}
		
		// add "&& ((config.getCookies().size() > 0))" incase of have no cookie		add at 2016.04.07
		if((! existCookiesInHeader) && ((config.getCookies().size() > 0)) ) {
			req.addHeader(Tools.COOKIE_STR, Tools.getCookieStr(config.getCookies()) );
		}
	}
	
}
