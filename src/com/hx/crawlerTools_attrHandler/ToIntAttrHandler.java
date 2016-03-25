/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.adapter.interf.NoneOrOneStringArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.OneStringArgsAttrHandler;

// 获取给定的字符串的长度的handler
// 用于适配类型
// map(toInt )	or map(toInt() )  or map(toInt('1') )
public class ToIntAttrHandler extends NoneOrOneStringArgsAttrHandler {
	
	// 初始化
	public ToIntAttrHandler(String str) {
		super(str);
	}
	public ToIntAttrHandler() {
		this(Constants.HANDLER_UNDEFINED);
	}

	@Override
	protected String gotResult(String str, String result) {
		return String.valueOf(Integer.parseInt(str) );
	}

	@Override
	public String name() {
		return Constants.TO_INT;
	}
	
}
