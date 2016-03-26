/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import com.hx.crawler.attrHandler.adapter.interf.NoneOrOneStringArgsAttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;

// 去掉字符串的所有空格的handler
// map(trimAll)
public class TrimAllAttrHandler extends NoneOrOneStringArgsAttrHandler {
	// 初始化
	public TrimAllAttrHandler(String arg) {
		super(arg);
	}
	public TrimAllAttrHandler() {
		this(Constants.HANDLER_UNDEFINED);
	}

	@Override
	protected String gotResult(String str, String result) {
		return Tools.trimAllSpaces(str);
	}

	@Override
	public String name() {
		return Constants.TRIM_ALL;
	}

}
