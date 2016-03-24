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

// ����˶��Handler��AttrHandler
public abstract class MultiArgsAttrHandler extends AttrHandler {
	
	// ������handler����
	protected List<AttrHandler> handlers;
	
	// ��ʼ��
	public MultiArgsAttrHandler(List<AttrHandler> handlers) {
		this.handlers = handlers;
	}
	public MultiArgsAttrHandler(int initCap) {
		this.handlers = new ArrayList<>(initCap);
	}
	public MultiArgsAttrHandler() {
		this(Constants.CONCATE_HANDLER_DEFAULT_CAP);
	}
	
	// ���handler
	public void addHandler(AttrHandler handler) {
		this.handlers.add(handler);
	}
	// ��ȡhandler
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
