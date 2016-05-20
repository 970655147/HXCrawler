/**
 * file name : OneOrTwoStringIntArgsAttrHandler.java
 * created at : 2:02:45 AM May 21, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter.interf;

import com.hx.crawler.attrHandler.interf.AttrHandler;

// 两个字符串, 一个int的AttrHandler
public abstract class TwoStringIntArgsAttrHandler extends AttrHandler {

	// 配置两个参数
	public abstract void setArgs(String arg01, String arg02, int arg03);
	
}
