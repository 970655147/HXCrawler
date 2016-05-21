/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import com.hx.crawler.attrHandler.adapter.interf.StringOneOrTwoIntAttrHandler;
import com.hx.crawler.util.Constants;

// 去掉字符串前后空格的handler
// map(trim), map(trim('abc'), map(trim('abc', 1) )
public class TrimAttrHandler extends StringOneOrTwoIntAttrHandler {
	
	// 初始化
	public TrimAttrHandler(String str, int trimHead, int trimTail) {
		super(str, trimHead, trimTail);
	}
	public TrimAttrHandler() {
		this(Constants.HANDLER_UNDEFINED, Constants.TARGET_UNDEFINED, Constants.TARGET_UNDEFINED);
	}
	
	@Override
	protected String gotResult(String target, int from, int to, String result) {
		String trimed = target.trim();
		if(from == Constants.TARGET_UNDEFINED) {
			from = 0;
		}
		if(to == Constants.TARGET_UNDEFINED) {
			to = trimed.length();
		} else {
			to = trimed.length() - to;
		}
		return trimed.substring(from, to);
	}
	
	@Override
	public String name() {
		return Constants.TRIM;
	}
	
}
