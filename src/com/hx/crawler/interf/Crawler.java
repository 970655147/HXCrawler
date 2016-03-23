/**
 * file name : Crawler.java
 * created at : 8:23:27 PM Jul 31, 2015
 * created by 970655147
 */

package com.hx.crawler.interf;

import java.io.IOException;

import org.apache.http.entity.ContentType;

import com.hx.crawlerTools_crawler.CrawlerConfig;
import com.hx.crawlerTools_crawler.Page;

// Cralwer ¹æ·¶
public abstract class Crawler {

	// url²ÎÊý
	protected ScriptParameter scriptParameter;
	
	// getPage
	public abstract Page getPage(String url) throws IOException;
	public abstract Page getPage(String url, CrawlerConfig config) throws IOException;
	
	// postPage
	public abstract Page postPage(String url) throws IOException;
	public abstract Page postPage(String url, CrawlerConfig config) throws IOException;
	public abstract Page postPage(String url, CrawlerConfig config, String bodyData, ContentType contentType) throws IOException;
	
	// setter & getter
	public ScriptParameter getScriptParameter() {
		return scriptParameter;
	}
	public void setScriptParameter(ScriptParameter scriptParameter) {
		this.scriptParameter = scriptParameter;
	}
	
	// ..
	
}
