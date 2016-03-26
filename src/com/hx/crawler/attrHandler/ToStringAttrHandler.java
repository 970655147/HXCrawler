/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import com.hx.crawler.attrHandler.adapter.interf.NoneOrOneStringArgsAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.OneStringArgsAttrHandler;
import com.hx.crawler.util.Constants;

// 获取给定的字符串的长度的handler
// 用于适配类型
// map(toInt )	or map(toInt() )  or map(toInt('1') )
public class ToStringAttrHandler extends NoneOrOneStringArgsAttrHandler {
	
	// 初始化
	public ToStringAttrHandler(String str) {
		super(str);
	}
	public ToStringAttrHandler() {
		this(Constants.HANDLER_UNDEFINED);
	}

	@Override
	protected String gotResult(String str, String result) {
		return str;
	}

	@Override
	public String name() {
		return Constants.TO_STRING;
	}
	
}
