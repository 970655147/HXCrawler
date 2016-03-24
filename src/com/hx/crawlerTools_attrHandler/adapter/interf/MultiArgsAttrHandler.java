/**
 * file name : MultiArgsAttrHandler.java
 * created at : 6:53:12 PM Mar 24, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler.adapter.interf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// 组合了多个Handler的AttrHandler
public abstract class MultiArgsAttrHandler extends AttrHandler {
	
	// 各个子handler条件
	protected List<AttrHandler> handlers;
	
	// 初始化
	public MultiArgsAttrHandler(List<AttrHandler> handlers) {
		this.handlers = handlers;
	}
	public MultiArgsAttrHandler(int initCap) {
		this.handlers = new ArrayList<>(initCap);
	}
	public MultiArgsAttrHandler() {
		this(Constants.CONCATE_HANDLER_DEFAULT_CAP);
	}
	
	// 添加handler
	public void addHandler(AttrHandler handler) {
		this.handlers.add(handler);
	}
	// 获取handler
	public AttrHandler handler(int idx) {
		if((handlers == null) || (idx < 0) || (idx >= handlers.size()) ) {
			return null;
		}
		
		return handlers.get(idx);
	}
	public List<AttrHandler> handlers() {
		return Collections.unmodifiableList(handlers);
	}
	public AttrHandler removeHandler(int idx) {
		if((handlers == null) || (idx < 0) || (idx >= handlers.size()) ) {
			return null;
		}
		
		return handlers.remove(idx);
	}
}
