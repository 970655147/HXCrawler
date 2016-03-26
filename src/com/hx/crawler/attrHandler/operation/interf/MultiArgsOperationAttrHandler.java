/**
 * file name : MultiArgsAttrHandler.java
 * created at : 6:53:12 PM Mar 24, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.operation.interf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// ����˶��Handler��AttrHandler
public abstract class MultiArgsOperationAttrHandler<T extends OperationAttrHandler> extends OperationAttrHandler {
	
	// ������handler����
	protected List<T> handlers;
	
	// ��ʼ��
	public MultiArgsOperationAttrHandler(List<T> handlers) {
		super();
		this.handlers = handlers;
	}
	public MultiArgsOperationAttrHandler(int initCap) {
		super();
		this.handlers = new ArrayList<>(initCap);
	}
	public MultiArgsOperationAttrHandler() {
		this(Constants.CONCATE_HANDLER_DEFAULT_CAP);
	}
	
	// ���handler
	public void addHandler(T handler) {
		this.handlers.add(handler);
		operationReturn(handler.operationReturn() );
	}
	// ��ȡhandler
	public T handler(int idx) {
		if((handlers == null) || (idx < 0) || (idx >= handlers.size()) ) {
			return null;
		}
		
		return handlers.get(idx);
	}
	public List<T> handlers() {
		return Collections.unmodifiableList(handlers);
	}
	public AttrHandler removeHandler(int idx) {
		if((handlers == null) || (idx < 0) || (idx >= handlers.size()) ) {
			return null;
		}
		
		return handlers.remove(idx);
	}
	
	@Override
	public void cleanImmediateReturnFlag() {
		super.cleanImmediateReturnFlag();
		for(OperationAttrHandler handler : handlers) {
			handler.cleanImmediateReturnFlag();
		}
	}
	
}
