/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.adapter.interf.OneOrTwoStringArgsAttrHandler;

// 判断给定的字符串是否和expect匹配的handler
// trim, length等等也具有此用法
// 1. 将$this和'abc'进行比较   2. 将传入的两个字符串进行比较
// map(equals('abc') ), map(equals('abc', 'df') )
public class NotEqualsAttrHandler extends OneOrTwoStringArgsAttrHandler {
	
	// 初始化
	public NotEqualsAttrHandler(String val, String expect) {
		super(val, expect);
	}
	public NotEqualsAttrHandler(String expect) {
		this(expect, Constants.HANDLER_UNDEFINED);
	}
	public NotEqualsAttrHandler() {
		this(Constants.HANDLER_UNDEFINED, Constants.HANDLER_UNDEFINED);
	}
	
	@Override
	protected String gotResult(String str, String expect, String result) {
		if(expect == null) {
			if(str == null) {
				return Constants.FALSE;
			} else {
				return Constants.TRUE;
			}
		}
		return String.valueOf(! expect.equals(str) );
	}

	@Override
	public String name() {
		return Constants.EQUALS;
	}
}
