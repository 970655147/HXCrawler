/**
 * file name : ThreeStringTwoBooleanArgsAttrHandler.java
 * created at : 11:39:48 PM May 20, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter.interf;

import com.hx.crawler.attrHandler.interf.AttrHandler;

public abstract class ThreeStringTwoBooleanArgsAttrHandler extends AttrHandler {

	// ���ü�������
	public abstract void setArgs(String target, String start, String end, boolean includeStart, boolean includeEnd);
	
}
