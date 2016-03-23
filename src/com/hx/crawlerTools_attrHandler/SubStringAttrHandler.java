/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;
import com.hx.crawlerTools_attrHandler.adapter.interf.TwoIntArgsAttrHandler;

// ½ØÈ¡×Ö·û´®µÄhandler
// map(subString(1, 3) )
public class SubStringAttrHandler extends TwoIntArgsAttrHandler {
	// ½ØÈ¡µÄ×Ö·û´®µÄ·¶Î§
	private int start;
	private int end;
	
	// ³õÊ¼»¯
	public SubStringAttrHandler(int start, int end) {
		setArgs(start, end);
	}
	public SubStringAttrHandler(int start) {
		setArgs(start, Constants.TARGET_UNDEFINED);
	}
	public SubStringAttrHandler() {
		setArgs(Constants.TARGET_UNDEFINED, Constants.TARGET_UNDEFINED);
	}

	@Override
	public String handle0(String result) {
		Tools.assert0(start > Constants.TARGET_UNDEFINED, "error while calc the 'subString(Int, Int)', 'start' be initialized illegal ! ");
		if(Constants.TARGET_UNDEFINED == end) {
			return result.substring(start);
		}
		
		return result.substring(start, end);
	}
	@Override
	public void setArgs(int arg1, int arg2) {
		start = arg1;
		end = arg2;
	}

	@Override
	public String name() {
		return Constants.SUB_STRING;
	}
	
}
