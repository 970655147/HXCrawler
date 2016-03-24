/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;
import com.hx.crawlerTools_attrHandler.adapter.interf.NoneOrOneStringArgsAttrHandler;

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
