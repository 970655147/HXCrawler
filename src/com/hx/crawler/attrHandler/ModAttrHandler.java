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
public class ModAttrHandler extends MultiArgsAttrHandler<AttrHandler> {
	
	// 初始化
	public ModAttrHandler(List<AttrHandler> handlerChain) {
		super(handlerChain);
	}
	public ModAttrHandler(int initCap) {
		super(initCap);
	}
	public ModAttrHandler() {
		super(Constants.CALC_HANDLER_DEFAULT_CAP);
	}

	@Override
	public String handle0(String result) {
		int res = Integer.parseInt(handlers.get(0).handle(result) );
		for(int i=1; i<handlers.size(); i++) {
			res %= Integer.parseInt(handlers.get(i).handle(result) );
		}
		
		return String.valueOf(res);
	}

	@Override
	public String name() {
		return Constants.MOD;
	}
}
