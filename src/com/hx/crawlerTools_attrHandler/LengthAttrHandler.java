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
// map(length )	or map(length() )
public class LengthAttrHandler extends NoneOrOneStringArgsAttrHandler {
	
	// 初始化
	public LengthAttrHandler(String str) {
		super(str);
	}
	public LengthAttrHandler() {
		this(Constants.HANDLER_UNDEFINED);
	}

	@Override
	protected String gotResult(String str, String result) {
		return String.valueOf(str.length() );
	}

	@Override
	public String name() {
		return Constants.TO_UPPERCASE;
	}
	
}
