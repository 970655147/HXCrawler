/**
 * file name : Main.java
 * created at : 3:46:03 PM Jul 26, 2015
 * created by 970655147
 */

package com.hx.crawler.test;

import java.io.IOException;

import org.apache.http.entity.ContentType;

import com.hx.crawler.crawler.CrawlerConfig;
import com.hx.crawler.crawler.HtmlCrawler;
import com.hx.crawler.crawler.Page;
import com.hx.crawler.interf.Crawler;
import com.hx.crawler.util.Log;
import com.hx.crawler.util.Tools;


// Main
public class Test02TestCrawler {

	// 1. 测试Crawler. getPage/ postPage
	public static void main(String []args) throws IOException {
		
		Crawler crawler = HtmlCrawler.newInstance();
		
////		String url = "http://www.shi.com";
////		String url = "http://www.csdn.net/";
//		String url = "http://www.baidu.com/";
//		Page page = crawler.getPage(url);
//		String html = page.getContent();
//		Tools.save(html, Tools.getTmpPath(2));
		
		String url = "https://www.shi.com/Products/ProductDetail.aspx/GetProductTabDate";
		CrawlerConfig config = new CrawlerConfig();
		config.addHeader("Content-Type", "application/json; charset=UTF-8");
		String bodyData = "{'tabText':'Specifications', 'productID': '24378925','cnetProdID':'S9525410','mfgDimID':'4294967094','ctgyDimID':'40068','curPriceString':'$200.00', 'HideLink':'0'}";
		Page page = crawler.postPage(url, config, bodyData, ContentType.APPLICATION_JSON);
		String html = page.getContent();
		// 但是不知道 为什么这个ajax返回来的数据  后面多了很长一截
		Tools.save(html, Tools.getTmpPath(2));
		Log.log(html);
		
		
		
	}
	
}
