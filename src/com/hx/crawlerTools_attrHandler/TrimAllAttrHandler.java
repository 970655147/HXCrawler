/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;

// 去掉字符串的所有空格的handler
// map(trimAll)
public class TrimAllAttrHandler extends AttrHandler {
	// 初始化
	public TrimAllAttrHandler() {
	}

	@Override
	public String handle0(String result) {
		return Tools.trimAllSpaces(result);
	}

	@Override
	public String name() {
		return Constants.TRIM_ALL;
	}
	
}
