/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// 什么都不做的AttrHandler
// map(doNothing)
public class DoNothingAttrHandler extends AttrHandler {
	
	// 初始化
	public DoNothingAttrHandler() {
	}

	@Override
	public String handle0(String result) {
		return result;
	}

	@Override
	public String name() {
		return Constants.DO_NOTHING;
	}
	
}
