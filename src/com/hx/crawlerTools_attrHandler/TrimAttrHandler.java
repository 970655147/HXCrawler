/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.adapter.interf.NoneOrOneStringArgsAttrHandler;

// 去掉字符串前后空格的handler
// map(trim), map(trim('abc')
public class TrimAttrHandler extends NoneOrOneStringArgsAttrHandler {
	
	// 初始化
	public TrimAttrHandler(String str) {
		super(str);
	}
	public TrimAttrHandler() {
		this(Constants.HANDLER_UNDEFINED);
	}

	@Override
	protected String gotResult(String str, String result) {
		return str.trim();
	}
	
	@Override
	public String name() {
		return Constants.TRIM;
	}
	
}
