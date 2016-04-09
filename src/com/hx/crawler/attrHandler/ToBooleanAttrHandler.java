/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import com.hx.crawler.attrHandler.adapter.interf.NoneOrOneStringArgsAttrHandler;
import com.hx.crawler.util.Constants;

// 获取给定的字符串的长度的handler
// map(toBool )	or map(toBool() )  or map(toBool('false') )
public class ToBooleanAttrHandler extends NoneOrOneStringArgsAttrHandler {
	
	// 初始化
	public ToBooleanAttrHandler(String str) {
		super(str);
	}
	public ToBooleanAttrHandler() {
		this(Constants.HANDLER_UNDEFINED);
	}

	@Override
	protected String gotResult(String str, String result) {
		return String.valueOf(Boolean.parseBoolean(str) );
	}

	@Override
	public String name() {
		return Constants.TO_BOOLEAN;
	}
	
}
