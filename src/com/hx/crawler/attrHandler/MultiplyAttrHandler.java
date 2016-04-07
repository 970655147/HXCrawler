/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import java.util.List;

import com.hx.crawler.attrHandler.adapter.interf.MultiArgsAttrHandler;
import com.hx.crawler.attrHandler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// 将给定的字符串替换为目标字符串
// map(replace(src, tar) )
public class MultiplyAttrHandler extends MultiArgsAttrHandler<AttrHandler> {
	
	// 初始化
	public MultiplyAttrHandler(List<AttrHandler> handlerChain) {
		super(handlerChain);
	}
	public MultiplyAttrHandler(int initCap) {
		super(initCap);
	}
	public MultiplyAttrHandler() {
		super(Constants.CALC_HANDLER_DEFAULT_CAP);
	}

	@Override
	public String handle0(String result) {
		int res = 1;
		for(AttrHandler handler : handlers) {
			res *= Integer.parseInt(handler.handle(result) );
		}
		
		return String.valueOf(res);
	}

	@Override
	public String name() {
		return Constants.MUL;
	}
}
