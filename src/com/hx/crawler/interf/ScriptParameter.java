/**
 * file name : ScriptParameter.java
 * created at : 8:19:53 PM Jul 31, 2015
 * created by 970655147
 */

package com.hx.crawler.interf;

import java.util.Map;

// �ű��Ĳ���
public abstract class ScriptParameter {
	
	// taskGroupId, taskId
	// �����url, ����, crawler
	protected int taskGroupId;
	protected int taskId;
	protected String url;
	protected Map<String, Object> param;
	protected Crawler crawler;
	
	// setter & getter
	public int getTaskGroupId() {
		return taskGroupId;
	}
	public void setTaskGroupId(int taskGroupId) {
		this.taskGroupId = taskGroupId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
	public void setParam(String key, Object value) {
		this.param.put(key, value);
	}
	public void setCrawler(Crawler crawler) {
		this.crawler = crawler;
	}
	public String getUrl() {
		return url;
	}
	public Map<String, Object> getParam() {
		return param;
	}
	public Crawler getCrawler() {
		return crawler;
	}
	public Object getParam(String key) {
		return param.get(key);
	}
	
	
}