/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;
import com.hx.crawlerTools_attrHandler.adapter.interf.NoneOrOneStringArgsAttrHandler;

// 将字符串的多个空格合并为一个的handler
// map(trimAsOne), map(trimAsOne('abc') )
public class TrimAsOneAttrHandler extends NoneOrOneStringArgsAttrHandler {
	
	// 初始化
	public TrimAsOneAttrHandler() {
		this(Constants.HANDLER_UNDEFINED);
	}
	public TrimAsOneAttrHandler(String str) {
		super(str);
	}
	
	@Override
	protected String gotResult(String str, String result) {
		return Tools.trimSpacesAsOne(str);
	}

	@Override
	public String name() {
		return Constants.TRIM_AS_ONE;
	}
	
}
