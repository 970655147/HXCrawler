/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;
import com.hx.crawlerTools_attrHandler.adapter.interf.OneStringArgsAttrHandler;

// 判断给定的result中是否包含contains
// 内部使用
public class ContainsAttrHandler extends OneStringArgsAttrHandler {
	// 需求包含的值
	private String contains;
	
	// 初始化
	public ContainsAttrHandler(String contains) {
		setArgs(contains);
	}
	public ContainsAttrHandler() {
		this(null);
	}

	@Override
	public String handle0(String result) {
		Tools.assert0(contains != null, "error while calc the 'matches(String)', pattern be initialized illegal ! ");
		return String.valueOf(result.contains(contains) );
	}

	@Override
	public String name() {
		return Constants.CONSTANTS;
	}

	@Override
	public void setArgs(String arg) {
		this.contains = arg;
	}
	
}
