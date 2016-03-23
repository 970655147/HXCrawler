/**
 * file name : CompositeAttrHandler.java
 * created at : 1:14:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import java.util.ArrayList;
import java.util.List;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// 包含一个attrHandler链的AttrHandler
public class CompositeAttrHandler extends AttrHandler {
	// attrHandler链
	private List<AttrHandler> handlerChain;

	// 初始化
	public CompositeAttrHandler(List<AttrHandler> handlerChain) {
		this.handlerChain = handlerChain;
	}
	public CompositeAttrHandler(int initCap) {
		this.handlerChain = new ArrayList<>(initCap);
	}
	public CompositeAttrHandler() {
		this(Constants.COMPOSITE_HANDLER_DEFAULT_CAP);
	}

	// 添加AttrHandler
	public void addHandler(AttrHandler handler) {
		handlerChain.add(handler);
	}
	
	@Override
	public String handle0(String result) {
		String res = result;
		for(AttrHandler handler : handlerChain) {
			res = handler.handle(res);
		}
		
		return res;
	}
	
	@Override
	public String name() {
		return Constants.COMPOSITE;
	}

}
