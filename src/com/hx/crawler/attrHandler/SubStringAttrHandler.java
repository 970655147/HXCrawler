/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import com.hx.crawler.attrHandler.adapter.interf.StringOneOrTwoIntAttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;

// 截取字符串的handler
// map(subString(1, 3) )
public class SubStringAttrHandler extends StringOneOrTwoIntAttrHandler {
	// 初始化
	public SubStringAttrHandler(String target, int from, int to) {
		super(target, from, to);
	}
	public SubStringAttrHandler(String target, int from) {
		super(target, from, Constants.TARGET_UNDEFINED);
	}
	public SubStringAttrHandler(int from, int to) {
		super(Constants.HANDLER_UNDEFINED, from, to);
	}
	public SubStringAttrHandler(int from) {
		super(Constants.HANDLER_UNDEFINED, from, Constants.TARGET_UNDEFINED);
	}
	public SubStringAttrHandler() {
		this(Constants.HANDLER_UNDEFINED, Constants.TARGET_UNDEFINED, Constants.TARGET_UNDEFINED);
	}
	
	@Override
	public String name() {
		return Constants.SUB_STRING;
	}
	
	@Override
	protected String gotResult(String target, int from, int to, String result) {
		if(to == Constants.TARGET_UNDEFINED) {
			to = target.length();
		}
		return target.substring(from, to);
	}
	
}
