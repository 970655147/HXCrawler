/**
 * file name : CompositeAttrHandler.java
 * created at : 1:14:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import java.util.List;

import com.hx.crawler.attrHandler.adapter.interf.MultiArgsAttrHandler;
import com.hx.crawler.attrHandler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// ����һ��attrHandler����AttrHandler
public class CompositeAttrHandler extends MultiArgsAttrHandler<AttrHandler> {
	// ��ʼ��
	public CompositeAttrHandler(List<AttrHandler> handlerChain) {
		super(handlerChain);
	}
	public CompositeAttrHandler(int initCap) {
		super(initCap);
	}
	public CompositeAttrHandler() {
		super(Constants.COMPOSITE_HANDLER_DEFAULT_CAP);
	}
	
	@Override
	public String handle0(String result) {
		String res = result;
		for(AttrHandler handler : handlers) {
			res = handler.handle(res);
		}
		
		return res;
	}
	
	@Override
	public String name() {
		return Constants.COMPOSITE;
	}

}
