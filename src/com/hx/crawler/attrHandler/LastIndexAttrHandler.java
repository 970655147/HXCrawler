/**
 * file name : IndexAttrHandler.java
 * created at : 1:12:29 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import com.hx.crawler.attrHandler.adapter.interf.OneOrTwoStringIntArgsAttrHandler;
import com.hx.crawler.util.Constants;

// lastIndexOf关联的AttrHandler
// map(lastIndexOf(hello[, 2]) )
public class LastIndexAttrHandler extends OneOrTwoStringIntArgsAttrHandler {
	// 初始化
	public LastIndexAttrHandler(String target, String idxStr, int from) {
		super(target, idxStr, from);
	}
	public LastIndexAttrHandler(String target, String idxStr) {
		this(target, idxStr, Constants.TARGET_UNDEFINED);
	}
	public LastIndexAttrHandler(String idxStr, int from) {
		super(Constants.HANDLER_UNDEFINED, idxStr, from);
	}
	public LastIndexAttrHandler(String idxStr) {
		this(Constants.HANDLER_UNDEFINED, idxStr, Constants.TARGET_UNDEFINED);
	}
	public LastIndexAttrHandler() {
		this(Constants.HANDLER_UNDEFINED, "", Constants.TARGET_UNDEFINED);
	}
	
	@Override
	public String name() {
		return Constants.LAST_INDEX_OF;
	}
	
	@Override
	protected String gotResult(String target, String idxStr, int from, String result) {
		if(from == Constants.TARGET_UNDEFINED) {
			from = target.length();
		}
		return String.valueOf(target.lastIndexOf(idxStr, from) );
	}
	
}
