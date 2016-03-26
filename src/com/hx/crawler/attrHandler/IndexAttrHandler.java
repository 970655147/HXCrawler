/**
 * file name : IndexAttrHandler.java
 * created at : 1:12:29 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import com.hx.crawler.attrHandler.adapter.interf.StringIntArgsAttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;

// indexOf关联的AttrHandler
// map(indexOf(hello[, 2]) )
public class IndexAttrHandler extends StringIntArgsAttrHandler {
	// 给定的目标字符串, 开始的位置
	private String idxStr;
	private int from;
	
	// 初始化
	public IndexAttrHandler(String idxStr, int from) {
		setArgs(idxStr, from);
	}
	public IndexAttrHandler(String idxStr) {
		this(idxStr, Constants.TARGET_UNDEFINED);
	}
	public IndexAttrHandler() {
		this(null, Constants.TARGET_UNDEFINED);
	}

	@Override
	public String handle0(String result) {
		Tools.assert0(idxStr != null, "error while calc the 'indexOf(String[, Int])', 'idxString' be initialized illegal ! ");
		if(Constants.TARGET_UNDEFINED == from) {
			return String.valueOf(result.indexOf(idxStr) );
		}
		
		return String.valueOf(result.indexOf(idxStr, from) );
	}
	
	@Override
	public String name() {
		return Constants.INDEX_OF;
	}
	
	@Override
	public void setArgs(String arg1, int arg2) {
		this.idxStr = arg1;
		this.from = arg2;
	}
	
}
