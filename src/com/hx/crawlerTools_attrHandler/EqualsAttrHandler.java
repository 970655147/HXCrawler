/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;
import com.hx.crawlerTools_attrHandler.adapter.interf.OneStringArgsAttrHandler;

// 判断给定的字符串是否和expect匹配的handler
// map(equals('abc') )
public class EqualsAttrHandler extends OneStringArgsAttrHandler {
	// 处理结果得到的常量值
	private String expect;
	
	// 初始化
	public EqualsAttrHandler(String expect) {
		this.expect = expect;
	}
	public EqualsAttrHandler() {
		this(null);
	}
	
	@Override
	public String handle0(String result) {
		if(expect == null) {
			if(result == null) {
				return Constants.TRUE;
			} else {
				return Constants.FALSE;
			}
		}
		return String.valueOf(expect.equals(result) );
	}

	@Override
	public String name() {
		return Constants.EQUALS;
	}

	@Override
	public void setArgs(String arg) {
		this.expect = arg;
	}
	
}
