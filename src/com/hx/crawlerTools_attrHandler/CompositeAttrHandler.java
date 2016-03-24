/**
 * file name : CompositeAttrHandler.java
 * created at : 1:14:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import java.util.List;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.adapter.interf.MultiArgsAttrHandler;

// 包含一个attrHandler链的AttrHandler
public class CompositeAttrHandler extends MultiArgsAttrHandler {
	// 初始化
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
