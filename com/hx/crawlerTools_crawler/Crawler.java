/**
 * file name : Crawler.java
 * created at : 8:23:27 PM Jul 31, 2015
 * created by 970655147
 */

package com.hx.crawlerTools_crawler;

import java.io.IOException;

import org.apache.http.entity.ContentType;

// Cralwer �淶
public abstract class Crawler {

	// url����
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
