/**
 * file name : AttrHandler.java
 * created at : 1:03:05 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.interf;

import com.hx.crawler.util.Tools;
import com.hx.crawlerTools_attrHandler.StandardHandlerParser.Types;

// handler相关的超类
public abstract class AttrHandler {
	// 初始化
	public AttrHandler() {
		
	}
	
	// 核心业务[模板方法]
	public String handle(String result) {
		Tools.assert0(result != null, "result can't be null !");
		String res = handle0(result);
		
		return res;
	}
	
	// 核心处理给定的字符串, 获取handler的名称
	protected abstract String handle0(String result);
	public abstract String name();
	
}
