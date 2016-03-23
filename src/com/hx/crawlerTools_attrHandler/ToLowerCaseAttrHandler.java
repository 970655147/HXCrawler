/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// 获取给定的字符串的小写形式的Handler
// map(toLowerCase )
public class ToLowerCaseAttrHandler extends AttrHandler {
	// 初始化
	public ToLowerCaseAttrHandler() {
	}

	@Override
	public String handle0(String result) {
		return result.toLowerCase();
	}

	@Override
	public String name() {
		return Constants.TO_LOWERCASE;
	}
	
}
