/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import com.hx.crawler.attrHandler.adapter.interf.NoneOrOneStringArgsAttrHandler;
import com.hx.crawler.util.Constants;

// 获取给定的字符串的大写形式的Handler
// map(toUpperCase ), map(toUpperCase('abc') )
public class ToUpperCaseAttrHandler extends NoneOrOneStringArgsAttrHandler {
	// 初始化
	public ToUpperCaseAttrHandler(String str) {
		super(str);
	}
	public ToUpperCaseAttrHandler() {
		this(Constants.HANDLER_UNDEFINED);
	}

	@Override
	protected String gotResult(String str, String result) {
		return str.toUpperCase();
	}

	@Override
	public String name() {
		return Constants.TO_UPPERCASE;
	}
	
}
